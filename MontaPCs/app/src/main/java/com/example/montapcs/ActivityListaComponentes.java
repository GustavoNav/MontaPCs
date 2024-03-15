package com.example.montapcs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ActivityListaComponentes extends AppCompatActivity implements AdapterComponente.OnItemClickListener {

    private SQLiteDatabase BancoDados;
    private RecyclerView recyclerView;

    List<Double> custoComponentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_componentes);

        getWindow().setStatusBarColor(Color.parseColor("#A4E9FF"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityListaComponentes.this, ActivityComputador.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        String componente = getIntent().getStringExtra("botaoClicado");
        String nomeComputador = getIntent().getStringExtra("nomeComputador");

        TextView textComponente = findViewById(R.id.textComponente);
        ImageView imageView = findViewById(R.id.imageView_1);

        textComponente.setText(componente);

        String caminhoImagem = determinarCaminhoImagem(componente)[0];
        String tabela = determinarCaminhoImagem(componente)[1];
        String id = determinarCaminhoImagem(componente)[2];

        String queryNomes = DefinirQuery(tabela,nomeComputador, id)[0];
        String queryCustos = DefinirQuery(tabela,nomeComputador, id)[1];

        custoComponentes = obterCustosComponentes(queryCustos);
        List<String> nomeComponentes = obterNomesComponentes(queryNomes);

        recyclerView = findViewById(R.id.recycleViewComponente);

        List<Componente> componentes = new ArrayList<>();
        for (int i = 0; i < nomeComponentes.size(); i++) {
            componentes.add(new Componente(nomeComponentes.get(i), custoComponentes.get(i)));
        }

        AdapterComponente adapter = new AdapterComponente(componentes);
        adapter.setOnItemClickListener(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (!caminhoImagem.isEmpty()) {
            int resourceId = getResources().getIdentifier(caminhoImagem, "drawable", getPackageName());
            if (resourceId != 0) {
                imageView.setImageResource(resourceId);
            }
        }
    }

    public String[] DefinirQuery(String tabela, String nomeComputador, String id) {
        String queryNomes = "";
        String queryCusto = "";

        switch (tabela) {
            case "placas_m":
                queryCusto = "SELECT custo FROM placas_m";
                queryNomes = "SELECT nome FROM placas_m";

                String idValor1 = VerificaComponente(nomeComputador, "cpus", "cpu_id");
                String idValor2 = VerificaComponente(nomeComputador, "mems_p", "mem_p_id");

                System.out.println("Valor 1: " + idValor1);
                System.out.println("Valor 2: " + idValor2);

                if (!idValor1.equals("0") && idValor2.equals("0")) {
                    String socket = RecursosPlacaM(nomeComputador)[4];
                    queryNomes += " WHERE socket = '" + socket + "'";
                    queryCusto += " WHERE socket = '" + socket + "'";
                    System.out.println("Socket : " + socket);
                }

                else if (!idValor2.equals("0") && idValor1.equals("0")) {
                    String[] recursosPlacaM = RecursosPlacaM(nomeComputador);
                    int velocidade = Integer.parseInt(recursosPlacaM[0]);
                    int capacidade = Integer.parseInt(recursosPlacaM[1]);
                    int modulos = Integer.parseInt(recursosPlacaM[2]);
                    String tipo_mem = recursosPlacaM[3];

                    queryNomes += " WHERE capacidade_m >= " + capacidade +
                            " AND velocidade_m >= " + velocidade +
                            " AND slots_m >= " + modulos +
                            " AND tipo_memoria = '" + tipo_mem + "'";
                    queryCusto += " WHERE capacidade_m >= " + capacidade +
                            " AND velocidade_m >= " + velocidade +
                            " AND slots_m >= " + modulos +
                            " AND tipo_memoria = '" + tipo_mem + "'";
                }

                else if (!idValor1.equals("0") && !idValor2.equals("0")) {
                    String[] recursosPlacaM = RecursosPlacaM(nomeComputador);
                    int velocidade = Integer.parseInt(recursosPlacaM[0]);
                    int capacidade = Integer.parseInt(recursosPlacaM[1]);
                    int modulos = Integer.parseInt(recursosPlacaM[2]);
                    String tipo_mem = recursosPlacaM[3];
                    String socket = recursosPlacaM[4];

                    queryNomes += " WHERE capacidade_m >= " + capacidade +
                            " AND velocidade_m >= " + velocidade +
                            " AND slots_m >= " + modulos +
                            " AND tipo_memoria = '" + tipo_mem + "'" +
                            " AND socket = '" + socket + "'";
                    queryCusto += " WHERE capacidade_m >= " + capacidade +
                            " AND velocidade_m >= " + velocidade +
                            " AND slots_m >= " + modulos +
                            " AND tipo_memoria = '" + tipo_mem + "'" +
                            " AND socket = '" + socket + "'";
                    System.out.println("Socket : " + socket);
                }

                System.out.println("Query nomes : " + queryNomes);
                System.out.println("Quer Custos : " + queryCusto);
                break;

            case "cpus":
                queryCusto = "SELECT custo FROM cpus";
                queryNomes = "SELECT nome FROM cpus";
                String idValor = VerificaComponente(nomeComputador, "placas_m", "placa_m_id");

                if (!idValor.equals("0")) {
                    String socket = RecursosCPU(nomeComputador);
                    queryNomes += " WHERE socket = '" + socket + "'";
                    queryCusto += " WHERE socket = '" + socket + "'";
                }
                break;

            case "gpus":
                queryCusto = "SELECT custo FROM gpus";
                queryNomes = "SELECT nome FROM gpus";
                break;

            case "mems_p":
                queryCusto = "SELECT custo FROM mems_p";
                queryNomes = "SELECT nome FROM mems_p";
                idValor = VerificaComponente(nomeComputador, "placas_m", "placa_m_id");
                if (!idValor.equals("0")) {
                    String[] recursosMemP = RecursosMemP(nomeComputador);
                    int velocidade = Integer.parseInt(recursosMemP[0]);
                    int capacidade = Integer.parseInt(recursosMemP[1]);
                    int modulos = Integer.parseInt(recursosMemP[2]);
                    String tipo_memoria = recursosMemP[3];


                    queryNomes += " WHERE capacidade <= " + capacidade +
                            " AND velocidade <= " + velocidade +
                            " AND modulos <= " + modulos +
                            " AND tipo_mem = '" + tipo_memoria + "'";
                    queryCusto += " WHERE capacidade <= " + capacidade +
                            " AND velocidade <= " + velocidade +
                            " AND modulos <= " + modulos +
                            " AND tipo_mem = '" + tipo_memoria + "'";

                    System.out.println("query: " + queryNomes);
                }
                break;

            case "mems_s":
                queryCusto = "SELECT custo FROM mems_s";
                queryNomes = "SELECT nome FROM mems_s";
                break;

            default:
                throw new IllegalArgumentException("Tabela desconhecida: " + tabela);
        }

        return new String[]{queryNomes, queryCusto};
    }

    public String VerificaComponente(String nomeComputador, String tabela, String id) {
        String idValor = "0";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT " + tabela + "." + id +
                    " FROM computadores" +
                    " INNER JOIN " + tabela + " ON computadores" + "." + id + " = " + tabela + "." + id +
                    " WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                idValor = cursor.getString(0);
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idValor;
    }

    public String RecursosCPU(String nomeComputador) {
        String socket = "";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT placas_m.socket " +
                    "FROM computadores " +
                    "INNER JOIN placas_m ON computadores.placa_m_id  = placas_m.placa_m_id "+
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                socket = cursor.getString(0);
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socket;
    }

    public String[] RecursosMemP(String nomeComputador) {
        String velocidade = "0";
        String capacidade = "0";
        String modulos = "0";
        String tipoMemoria = "";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT placas_m.capacidade_m, placas_m.velocidade_m, placas_m.slots_m, placas_m.tipo_memoria " +
                    "FROM computadores " +
                    "INNER JOIN placas_m ON computadores.placa_m_id = placas_m.placa_m_id " +
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                capacidade = cursor.getString(0);
                velocidade = cursor.getString(1);
                modulos = cursor.getString(2);
                tipoMemoria = cursor.getString(3);
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{velocidade, capacidade, modulos, tipoMemoria};
    }

    public String[] RecursosPlacaM(String nomeComputador) {
        String velocidade = "0";
        String capacidade = "0";
        String modulos = "0";
        String tipoMemoria = "";
        String socket = "";

        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT mems_p.capacidade, mems_p.velocidade, mems_p.modulos, mems_p.tipo_mem " +
                    "FROM computadores " +
                    "INNER JOIN mems_p ON computadores.mem_p_id = mems_p.mem_p_id " +
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                capacidade = cursor.getString(0);
                velocidade = cursor.getString(1);
                modulos = cursor.getString(2);
                tipoMemoria = cursor.getString(3);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT cpus.socket " +
                    "FROM computadores " +
                    "INNER JOIN cpus ON computadores.cpu_id  = cpus.cpu_id "+
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                socket = cursor.getString(0);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String[]{velocidade, capacidade, modulos, tipoMemoria, socket};
    }

    @Override
    public void onItemClick(String nomeComponente) {
        String componente = getIntent().getStringExtra("botaoClicado");
        String infoComponente = construirInformacoesComponente(nomeComponente, componente);

        String nomeComputador = getIntent().getStringExtra("nomeComputador");
        String tabela = determinarCaminhoImagem(componente)[1];
        String idColumn = determinarCaminhoImagem(componente)[2];

        String idComponente = obterIdComponente(nomeComponente, tabela, idColumn);

        DialogComponente.showDialog(ActivityListaComponentes.this, infoComponente, new DialogComponente.OnSelectListener() {
            @Override
            public void onSelect() {
                if (idComponente != null) {
                    atualizarIdComputador(nomeComputador, idColumn,idComponente);

                    Toast.makeText(ActivityListaComponentes.this, "Seleção bem sucedida", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });
    }

    @SuppressLint("Range")
    private String obterIdComponente(String nomeComponente, String tabela, String idColumn) {
        String id = null;
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT " + idColumn + " FROM " + tabela + " WHERE nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComponente});
            if (cursor.moveToFirst()) {
                id = cursor.getString(cursor.getColumnIndex(idColumn));
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    private void atualizarIdComputador(String nomeComputador, String idColumn, String idComponente) {
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "UPDATE computadores SET " + idColumn + " = ? WHERE nome = ?";
            SQLiteStatement statement = BancoDados.compileStatement(query);
            statement.bindString(1, idComponente);
            statement.bindString(2, nomeComputador);
            statement.executeUpdateDelete();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] determinarCaminhoImagem(String componente) {
        String caminhoImagemVariavel = "";
        String tabela = "";
        String id = "";

        // Determinar o caminho da primeira imagem com base no componente recebido
        if ("Placa Mãe".equals(componente)) {
            caminhoImagemVariavel = "motherboard";
            tabela = "placas_m";
            id = "placa_m_id";
        } else if ("Processador (CPU)".equals(componente)) {
            caminhoImagemVariavel = "cpu";
            tabela = "cpus";
            id = "cpu_id";
        } else if ("Placa de Video (GPU)".equals(componente)) {
            caminhoImagemVariavel = "gpu";
            tabela = "gpus";
            id = "gpu_id";
        } else if ("Memória 1 - RAM".equals(componente)) {
            caminhoImagemVariavel = "ram";
            tabela = "mems_p";
            id = "mem_p_id";
        } else if ("Memória 2 - SSD/HD".equals(componente)) {
            caminhoImagemVariavel = "mem";
            tabela = "mems_s";
            id = "mem_s_id";
        }
        return new String[]{caminhoImagemVariavel, tabela, id};
    }

    public List<String> obterNomesComponentes(String query) {
        List<String> nomesComponentes = new ArrayList<>();
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            Cursor cursor = BancoDados.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String nomeComponente = cursor.getString(cursor.getColumnIndex("nome"));
                    nomesComponentes.add(nomeComponente);
                } while (cursor.moveToNext());
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nomesComponentes;
    }

    public List<Double> obterCustosComponentes(String query) {
        List<Double> custos = new ArrayList<>();
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            Cursor cursor = BancoDados.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") double custo = cursor.getDouble(cursor.getColumnIndex("custo"));
                    custos.add(custo);
                } while (cursor.moveToNext());
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return custos;
    }

    private String construirInformacoesComponente(String NomeComponente, String Componente) {
        String info = "aeae";

        // Chama a função correspondente com base no nome do componente
        if ("Placa Mãe".equals(Componente)) {
            info = obterInformacoesPlacaMae(NomeComponente);
        } else if ("Processador (CPU)".equals(Componente)) {
            info = obterInformacoesCPU(NomeComponente);
        } else if ("Placa de Video (GPU)".equals(Componente)) {
            info = obterInformacoesGPU(NomeComponente);
        } else if ("Memória 1 - RAM".equals(Componente)) {
            info = obterInformacoesMemoriaRAM(NomeComponente);
        } else if ("Memória 2 - SSD/HD".equals(Componente)) {
            info = obterInformacoesMemoriaSecundaria(NomeComponente);
        }

        return info;
    }

    @SuppressLint("Range")
    private String obterInformacoesPlacaMae(String nomeComponente) {
        String informacoes = "";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT * FROM placas_m WHERE nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComponente});
            if (cursor.moveToFirst()) {
                informacoes = "Custo: " + cursor.getDouble(cursor.getColumnIndex("custo")) + "\n" +
                        "Fabricante: " + cursor.getString(cursor.getColumnIndex("fabricante")) + "\n" +
                        "Socket: " + cursor.getString(cursor.getColumnIndex("socket")) + "\n" +
                        "Slots de Memória: " + cursor.getInt(cursor.getColumnIndex("slots_m")) + "\n" +
                        "Capacidade de Memória: " + cursor.getInt(cursor.getColumnIndex("capacidade_m")) + "\n" +
                        "Velocidade de Memória: " + cursor.getInt(cursor.getColumnIndex("velocidade_m")) + "\n" +
                        "WiFi: " + cursor.getString(cursor.getColumnIndex("wifi")) + "\n" +
                        "Tipo Memória: " + cursor.getString(cursor.getColumnIndex("tipo_memoria"));
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return informacoes;
    }

    @SuppressLint("Range")
    private String obterInformacoesCPU(String nomeComponente) {
        String informacoes = "";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT * FROM cpus WHERE nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComponente});
            if (cursor.moveToFirst()) {
                informacoes = "Custo: " + cursor.getDouble(cursor.getColumnIndex("custo")) + "\n" +
                        "Fabricante: " + cursor.getString(cursor.getColumnIndex("fabricante")) + "\n" +
                        "Socket: " + cursor.getString(cursor.getColumnIndex("socket")) + "\n" +
                        "Frequência Base: " + cursor.getInt(cursor.getColumnIndex("frequencia_b")) + " MHz\n" +
                        "Frequência Turbo: " + cursor.getInt(cursor.getColumnIndex("frequencia_t")) + " MHz\n" +
                        "Núcleos: " + cursor.getInt(cursor.getColumnIndex("nucleos")) + "\n" +
                        "Threads: " + cursor.getInt(cursor.getColumnIndex("threads")) + "\n" +
                        "GPU Integrada: " + cursor.getString(cursor.getColumnIndex("gpu_integrada")) + "\n" +
                        "Nome da GPU: " + cursor.getString(cursor.getColumnIndex("gpu_nome")) + "\n" +
                        "Cache L1: " + cursor.getString(cursor.getColumnIndex("cache_l1")) + "\n" +
                        "Cache L2: " + cursor.getString(cursor.getColumnIndex("cache_l2")) + "\n" +
                        "Cache L3: " + cursor.getString(cursor.getColumnIndex("cache_l3"));
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return informacoes;
    }

    @SuppressLint("Range")
    private String obterInformacoesGPU(String nomeComponente) {
        String informacoes = "";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT * FROM gpus WHERE nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComponente});
            if (cursor.moveToFirst()) {
                informacoes = "Custo: " + cursor.getDouble(cursor.getColumnIndex("custo")) + "\n" +
                        "Fabricante: " + cursor.getString(cursor.getColumnIndex("fabricante")) + "\n" +
                        "Série: " + cursor.getString(cursor.getColumnIndex("serie")) + "\n" +
                        "Interface: " + cursor.getString(cursor.getColumnIndex("interface")) + "\n" +
                        "Tipo de Memória: " + cursor.getString(cursor.getColumnIndex("tipo_memoria")) + "\n" +
                        "Tamanho da Memória: " + cursor.getInt(cursor.getColumnIndex("tamanho_memoria")) + " GB\n" +
                        "Clock Base: " + cursor.getInt(cursor.getColumnIndex("clock_b")) + " MHz";
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return informacoes;
    }

    @SuppressLint("Range")
    private String obterInformacoesMemoriaRAM(String nomeComponente) {
        String informacoes = "";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT * FROM mems_p WHERE nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComponente});
            if (cursor.moveToFirst()) {
                informacoes = "Custo: " + cursor.getDouble(cursor.getColumnIndex("custo")) + "\n" +
                        "Fabricante: " + cursor.getString(cursor.getColumnIndex("fabricante")) + "\n" +
                        "Capacidade: " + cursor.getInt(cursor.getColumnIndex("capacidade")) + " GB\n" +
                        "Velocidade: " + cursor.getInt(cursor.getColumnIndex("velocidade")) + " MHz\n" +
                        "Quantidade de Módulos: " + cursor.getInt(cursor.getColumnIndex("modulos")) + "\n" +
                        "Tipo de Memória: " + cursor.getString(cursor.getColumnIndex("tipo_mem"));
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return informacoes;
    }

    @SuppressLint("Range")
    private String obterInformacoesMemoriaSecundaria(String nomeComponente) {
        String informacoes = "";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT * FROM mems_s WHERE nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComponente});
            if (cursor.moveToFirst()) {
                informacoes = "Custo: " + cursor.getDouble(cursor.getColumnIndex("custo")) + "\n" +
                        "Fabricante: " + cursor.getString(cursor.getColumnIndex("fabricante")) + "\n" +
                        "Tipo: " + cursor.getString(cursor.getColumnIndex("tipo")) + "\n" +
                        "Capacidade: " + cursor.getInt(cursor.getColumnIndex("capacidade")) + " GB\n" +
                        "Formato: " + cursor.getString(cursor.getColumnIndex("formato")) + "\n" +
                        "Velocidade de Leitura: " + cursor.getInt(cursor.getColumnIndex("velocidade_l")) + " MB/s\n" +
                        "Velocidade de Escrita: " + cursor.getInt(cursor.getColumnIndex("velocidade_e")) + " MB/s";
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return informacoes;
    }
}