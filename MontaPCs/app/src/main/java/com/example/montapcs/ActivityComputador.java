package com.example.montapcs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class ActivityComputador extends AppCompatActivity {
    private SQLiteDatabase BancoDados; // Banco de Dados

    @Override
    protected void onResume() {
        super.onResume();
        atualizarNomesComponentes();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computador);

        getWindow().setStatusBarColor(Color.parseColor("#A4E9FF"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityComputador.this, ActivityMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        String nomeComputador = getIntent().getStringExtra("nomeComputador");
        TextView textViewNomeComputador = findViewById(R.id.textViewNomePC);
        textViewNomeComputador.setText(nomeComputador);

        String placaMae = obterNomePlacaMae(nomeComputador);
        String cpu = obterNomeCPU(nomeComputador);
        String gpu = obterNomeGPU(nomeComputador);
        String memoriaPrincipal = obterNomeMemoriaPrincipal(nomeComputador);
        String memoriaSecundaria = obterNomeMemoriaSecundaria(nomeComputador);

        String custoPlacaMae = obterCustoPlacaMae(nomeComputador);
        String custoCPU = obterCustoCPU(nomeComputador);
        String custoGPU = obterCustoGPU(nomeComputador);
        String custoMemoriaPrincipal = obterCustoMemoriaPrincipal(nomeComputador);
        String custoMemoriaSecundaria = obterCustoMemoriaSecundaria(nomeComputador);

        // Conjunto 1
        View conjunto1 = findViewById(R.id.rectangle_1);
        ImageView icon1 = findViewById(R.id.icon_1);
        TextView textView1 = findViewById(R.id.textView1);

        // Conjunto 2
        View conjunto2 = findViewById(R.id.rectangle_2);
        ImageView icon2 = findViewById(R.id.icon_2);
        TextView textView2 = findViewById(R.id.textView2);

        // Conjunto 3
        View conjunto3 = findViewById(R.id.rectangle_3);
        ImageView icon3 = findViewById(R.id.icon_3);
        TextView textView3 = findViewById(R.id.textView3);

        // Conjunto 4
        View conjunto4 = findViewById(R.id.rectangle_4);
        ImageView icon4 = findViewById(R.id.icon_4);
        TextView textView4 = findViewById(R.id.textView4);

        // Conjunto 5
        View conjunto5 = findViewById(R.id.rectangle_5);
        ImageView icon5 = findViewById(R.id.icon_5);
        TextView textView5 = findViewById(R.id.textView5);

        textView1.setText(Html.fromHtml("<b>Placa Mãe</b>" + "<br/><b>Atual:</b> " + placaMae + "<br/><b>Custo:</b> R$" + custoPlacaMae));
        textView2.setText(Html.fromHtml("<b>Processador (CPU)</b>" + "<br/><b>Atual:</b> " + cpu + "<br/><b>Custo:</b> R$" + custoCPU));
        textView3.setText(Html.fromHtml("<b>Placa de Video (GPU)</b>" + "<br/><b>Atual:</b> " + gpu + "<br/><b>Custo:</b> R$" + custoGPU));
        textView4.setText(Html.fromHtml("<b>Memória Principal(RAM)</b>" + "<br/><b>Atual:</b> " + memoriaPrincipal + "<br/><b>Custo:</b> R$" + custoMemoriaPrincipal));
        textView5.setText(Html.fromHtml("<b>Memória Secundária(SSD/HD)</b>" + "<br/><b>Atual:</b> " + memoriaSecundaria + "<br/><b>Custo:</b> R$" + custoMemoriaSecundaria));

        ImageView imageView = findViewById(R.id.quadro2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirAjudaActivity();
            }
        });

        double custoTotal = calcularCustoTotal(nomeComputador);
        TextView textViewCusto = findViewById(R.id.textViewCusto);
        textViewCusto.setText("Custo Total: R$" + custoTotal);

        conjunto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Placa Mãe");
            }
        });
        icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Placa Mãe");
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Placa Mãe");
            }
        });

        conjunto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Processador (CPU)");
            }
        });
        icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Processador (CPU)");
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Processador (CPU)");
            }
        });

        conjunto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Placa de Video (GPU)");
            }
        });
        icon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Placa de Video (GPU)");
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Placa de Video (GPU)");
            }
        });

        conjunto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Memória 1 - RAM");
            }
        });
        icon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Memória 1 - RAM");
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Memória 1 - RAM");
            }
        });

        conjunto5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Memória 2 - SSD/HD");
            }
        });
        icon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Memória 2 - SSD/HD");
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNovaTela("Memória 2 - SSD/HD");
            }
        });
    }

    private void atualizarNomesComponentes() {
        String nomeComputador = getIntent().getStringExtra("nomeComputador");
        String placaMae = obterNomePlacaMae(nomeComputador);
        String custoPlacaMae = obterCustoPlacaMae(nomeComputador);
        String cpu = obterNomeCPU(nomeComputador);
        String custoCPU = obterCustoCPU(nomeComputador);
        String gpu = obterNomeGPU(nomeComputador);
        String custoGPU = obterCustoGPU(nomeComputador);
        String memoriaPrincipal = obterNomeMemoriaPrincipal(nomeComputador);
        String custoMemoriaPrincipal = obterCustoMemoriaPrincipal(nomeComputador);
        String memoriaSecundaria = obterNomeMemoriaSecundaria(nomeComputador);
        String custoMemoriaSecundaria = obterCustoMemoriaSecundaria(nomeComputador);

        double custoTotal = calcularCustoTotal(nomeComputador);
        Log.d("CustoTotal", "O custo total é: " + custoTotal);
        atualizarCustoTotal(nomeComputador, custoTotal);

        // Atualizar os botões com os novos nomes e custos dos componentes
        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);
        TextView textView4 = findViewById(R.id.textView4);
        TextView textView5 = findViewById(R.id.textView5);

        textView1.setText(Html.fromHtml("<b>Placa Mãe</b>" + "<br/><b>Atual:</b> " + placaMae + "<br/><b>Custo:</b> R$" + custoPlacaMae));
        textView2.setText(Html.fromHtml("<b>Processador (CPU)</b>" + "<br/><b>Atual:</b> " + cpu + "<br/><b>Custo:</b> R$" + custoCPU));
        textView3.setText(Html.fromHtml("<b>Placa de Video (GPU)</b>" + "<br/><b>Atual:</b> " + gpu + "<br/><b>Custo:</b> R$" + custoGPU));
        textView4.setText(Html.fromHtml("<b>Memória Principal(RAM)</b>" + "<br/><b>Atual:</b> " + memoriaPrincipal + "<br/><b>Custo:</b> R$" + custoMemoriaPrincipal));
        textView5.setText(Html.fromHtml("<b>Memória Secundária(SSD/HD)</b>" + "<br/><b>Atual:</b> " + memoriaSecundaria + "<br/><b>Custo:</b> R$" + custoMemoriaSecundaria));


    }

    private void abrirNovaTela(String botaoClicado) {
        // Obtendo o nome do computador passado da MainActivity
        String nomeComputador = getIntent().getStringExtra("nomeComputador");

        Intent intent = new Intent(ActivityComputador.this, ActivityListaComponentes.class);
        intent.putExtra("botaoClicado", botaoClicado);
        intent.putExtra("nomeComputador", nomeComputador);
        startActivity(intent);
    }

    private void abrirAjudaActivity() {
        Intent intent = new Intent(this, ActivityAjuda.class);
        startActivity(intent);
    }

    @SuppressLint("Range")
    private String obterNomePlacaMae(String nomeComputador) {
        String nome = "";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT placas_m.nome " +
                    "FROM computadores " +
                    "INNER JOIN placas_m ON computadores.placa_m_id = placas_m.placa_m_id " +
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                nome = cursor.getString(0);
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nome;
    }

    @SuppressLint("Range")
    private String obterNomeCPU(String nomeComputador) {
        String nome = "";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT cpus.nome " +
                    "FROM computadores " +
                    "INNER JOIN cpus ON computadores.cpu_id = cpus.cpu_id " +
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                nome = cursor.getString(0);
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nome;
    }

    @SuppressLint("Range")
    private String obterNomeGPU(String nomeComputador) {
        String nome = "";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT gpus.nome " +
                    "FROM computadores " +
                    "INNER JOIN gpus ON computadores.gpu_id = gpus.gpu_id " +
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                nome = cursor.getString(0);
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nome;
    }

    @SuppressLint("Range")
    private String obterNomeMemoriaPrincipal(String nomeComputador) {
        String nome = "";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT mems_p.nome " +
                    "FROM computadores " +
                    "INNER JOIN mems_p ON computadores.mem_p_id = mems_p.mem_p_id " +
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                nome = cursor.getString(0);
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nome;
    }

    @SuppressLint("Range")
    private String obterNomeMemoriaSecundaria(String nomeComputador) {
        String nome = "";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT mems_s.nome " +
                    "FROM computadores " +
                    "INNER JOIN mems_s ON computadores.mem_s_id = mems_s.mem_s_id " +
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                nome = cursor.getString(0);
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nome;
    }

    @SuppressLint("Range")
    private String obterCustoPlacaMae(String nomeComputador) {
        String custo = "0";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT placas_m.custo " +
                    "FROM computadores " +
                    "INNER JOIN placas_m ON computadores.placa_m_id = placas_m.placa_m_id " +
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                custo = cursor.getString(0);
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return custo;
    }

    @SuppressLint("Range")
    private String obterCustoCPU(String nomeComputador) {
        String custo = "0";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT cpus.custo " +
                    "FROM computadores " +
                    "INNER JOIN cpus ON computadores.cpu_id = cpus.cpu_id " +
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                custo = cursor.getString(0);
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return custo;
    }

    private String obterCustoGPU(String nomeComputador) {
        String custo = "0";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT gpus.custo " +
                    "FROM computadores " +
                    "INNER JOIN gpus ON computadores.gpu_id = gpus.gpu_id " +
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                custo = cursor.getString(0);
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return custo;
    }

    @SuppressLint("Range")
    private String obterCustoMemoriaPrincipal(String nomeComputador) {
        String custo = "0";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT mems_p.custo " +
                    "FROM computadores " +
                    "INNER JOIN mems_p ON computadores.mem_p_id = mems_p.mem_p_id " +
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                custo = cursor.getString(0);
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return custo;
    }

    @SuppressLint("Range")
    private String obterCustoMemoriaSecundaria(String nomeComputador) {
        String custo = "0";
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT mems_s.custo " +
                    "FROM computadores " +
                    "INNER JOIN mems_s ON computadores.mem_s_id = mems_s.mem_s_id " +
                    "WHERE computadores.nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeComputador});
            if (cursor.moveToFirst()) {
                custo = cursor.getString(0);
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return custo;
    }

    private double calcularCustoTotal(String nomeComputador) {
        double custoTotal = 0.0;

        String custoPlacaMae = obterCustoPlacaMae(nomeComputador);
        String custoCPU = obterCustoCPU(nomeComputador);
        String custoGPU = obterCustoGPU(nomeComputador);
        String custoMemoriaPrincipal = obterCustoMemoriaPrincipal(nomeComputador);
        String custoMemoriaSecundaria = obterCustoMemoriaSecundaria(nomeComputador);

        double custoPlacaMaeDouble = parseDoubleOrDefault(custoPlacaMae, 0.0);
        double custoCPUDouble = parseDoubleOrDefault(custoCPU, 0.0);
        double custoGPUDouble = parseDoubleOrDefault(custoGPU, 0.0);
        double custoMemoriaPrincipalDouble = parseDoubleOrDefault(custoMemoriaPrincipal, 0.0);
        double custoMemoriaSecundariaDouble = parseDoubleOrDefault(custoMemoriaSecundaria, 0.0);

        custoTotal = custoPlacaMaeDouble + custoCPUDouble + custoGPUDouble + custoMemoriaPrincipalDouble + custoMemoriaSecundariaDouble;

        return custoTotal;
    }

    private double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void atualizarCustoTotal(String nomeComputador, double custoTotal) {
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "UPDATE computadores SET valor = ? WHERE nome = ?";
            BancoDados.execSQL(query, new String[]{String.valueOf(custoTotal), nomeComputador});
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView textViewCusto = findViewById(R.id.textViewCusto);
        textViewCusto.setText("Custo Total: R$" + custoTotal);
    }
}