package com.example.montapcs;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity {
    private SQLiteDatabase BancoDados;
    private RecyclerView recyclerView;
    private AdapterComputador adapter;

    @Override
    protected void onResume() {
        super.onResume();
        atualizarListaComputadores();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        atualizarListaComputadores();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(Color.parseColor("#A4E9FF"));
        String nomeComputador = getIntent().getStringExtra("nomeComputador");

        recyclerView = findViewById(R.id.RecyclerComputadores);

        adapter = new AdapterComputador(this, new ArrayList<>());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        criarBancoDados();

        Button buttonCriar = findViewById(R.id.buttonCriar);
        buttonCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarDialogo();
            }
        });

        atualizarListaComputadores();

//        inserirDadosPlacasMae();
//        inserirDadosProcessadores();
//        inserirDadosPlacasVideo();
//        inserirDadosMemoriasPrincipais();
//        inserirDadosMemoriasSecundarias();
    }

    private void atualizarListaComputadores() {
        List<Computador> computadores = obterComputadoresDoBancoDeDados();
        adapter.atualizarLista(computadores);
    }

    public List<Computador> obterComputadoresDoBancoDeDados() {
        List<Computador> computadores = new ArrayList<>();
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT nome, valor FROM computadores ORDER BY nome";
            Cursor cursor = BancoDados.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String nomeComputador = cursor.getString(cursor.getColumnIndex("nome"));
                    @SuppressLint("Range") double valor = cursor.getDouble(cursor.getColumnIndex("valor"));
                    computadores.add(new Computador(nomeComputador, valor));
                } while (cursor.moveToNext());
            }
            cursor.close();
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return computadores;
    }

    private void criarDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insira o nome do projeto");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Criar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nomeProjeto = input.getText().toString();

                // Verificar se o nome já existe no banco de dados
                if (verificarNomeProjeto(nomeProjeto)) {
                    Toast.makeText(ActivityMain.this, "Nome em uso!", Toast.LENGTH_SHORT).show();
                } else {
                    inserirProjeto(nomeProjeto, 0);
                    Toast.makeText(ActivityMain.this, "Projeto Criado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private boolean verificarNomeProjeto(String nomeProjeto) {
        boolean nomeExiste = false;
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "SELECT COUNT(*) FROM computadores WHERE nome = ?";
            Cursor cursor = BancoDados.rawQuery(query, new String[]{nomeProjeto});
            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                nomeExiste = (count > 0);
            }
            if (cursor != null) {
                cursor.close();
            }
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nomeExiste;
    }


// ABAIXO - Funções do Banco de Dados

    public void criarBancoDados() {
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);

            // Criar tabela computadores
            BancoDados.execSQL("CREATE TABLE IF NOT EXISTS computadores(" +
                    " projeto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " nome VARCHAR," +
                    " valor NUMERIC," +
                    " placa_m_id INTEGER," +
                    " cpu_id INTEGER," +
                    " gpu_id INTEGER," +
                    " mem_p_id INTEGER," +
                    " mem_s_id INTEGER," +
                    " FOREIGN KEY (placa_m_id) REFERENCES placas_m (placa_m_id)," +
                    " FOREIGN KEY (cpu_id) REFERENCES cpus (cpu_id)," +
                    " FOREIGN KEY (gpu_id) REFERENCES gpus (gpu_id)," +
                    " FOREIGN KEY (mem_p_id) REFERENCES mems_p (mem_p_id)," +
                    " FOREIGN KEY (mem_s_id) REFERENCES mems_s (mem_s_id)" +
                    ")");

            // Criar tabela placas_m
            BancoDados.execSQL("CREATE TABLE IF NOT EXISTS placas_m (" +
                    " placa_m_id INTEGER PRIMARY KEY," +
                    " custo NUMERIC," +
                    " nome VARCHAR," +
                    " fabricante VARCHAR," +
                    " socket VARCHAR," +
                    " slots_m INTEGER," +
                    " capacidade_m INTEGER," +
                    " velocidade_m INTEGER," +
                    " wifi VARCHAR," +
                    " tipo_memoria VARCHAR" +
                    ")");

            // Criar tabela cpus
            BancoDados.execSQL("CREATE TABLE IF NOT EXISTS cpus (" +
                    " cpu_id INTEGER PRIMARY KEY," +
                    " custo NUMERIC," +
                    " nome VARCHAR," +
                    " fabricante VARCHAR," +
                    " socket VARCHAR," +
                    " frequencia_b NUMERIC," +
                    " frequencia_t NUMERIC," +
                    " nucleos INTEGER," +
                    " threads INTEGER," +
                    " gpu_integrada VARCHAR," +
                    " gpu_nome VARCHAR," +
                    " cache_l1 VARCHAR," +
                    " cache_l2 VARCHAR," +
                    " cache_l3 VARCHAR" +
                    ")");

            // Criar tabela gpus
            BancoDados.execSQL("CREATE TABLE IF NOT EXISTS gpus (" +
                    " gpu_id INTEGER PRIMARY KEY," +
                    " custo NUMERIC," +
                    " nome VARCHAR," +
                    " fabricante VARCHAR," +
                    " serie VARCHAR," +
                    " interface VARCHAR," +
                    " tipo_memoria VARCHAR," +
                    " tamanho_memoria INTEGER," +
                    " clock_b NUMERIC" +
                    ")");

            // Criar tabela mems_p
            BancoDados.execSQL("CREATE TABLE IF NOT EXISTS mems_p (" +
                    " mem_p_id INTEGER PRIMARY KEY," +
                    " custo NUMERIC," +
                    " nome VARCHAR," +
                    " fabricante VARCHAR," +
                    " capacidade INTEGER," +
                    " velocidade INTEGER," +
                    " modulos INTEGER," +
                    " tipo_mem VARCHAR" +
                    ")");

            // Criar tabela mems_s
            BancoDados.execSQL("CREATE TABLE IF NOT EXISTS mems_s (" +
                    " mem_s_id INTEGER PRIMARY KEY," +
                    " custo NUMERIC," +
                    " nome VARCHAR," +
                    " fabricante VARCHAR," +
                    " tipo VARCHAR," +
                    " capacidade INTEGER," +
                    " formato VARCHAR," +
                    " velocidade_l INTEGER," +
                    " velocidade_e INTEGER" +
                    ")");

            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inserirProjeto(String nome, int valor) {
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "INSERT INTO computadores (nome, valor) VALUES (?, ?)";
            BancoDados.execSQL(query, new Object[]{nome, valor});
            BancoDados.close();

            // Após a inserção, atualize a lista de computadores e notifique o adaptador
            List<Computador> computadores = obterComputadoresDoBancoDeDados();
            AdapterComputador adapter = new AdapterComputador(this, computadores);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inserirPlacaMae(double custo, String nome, String fabricante, String socket, int slots_m, int capacidade_m, int velocidade_m, String wifi, String tipo_memoria) {
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "INSERT INTO placas_m (custo, nome, fabricante, socket, slots_m, capacidade_m, velocidade_m, wifi, tipo_memoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
            BancoDados.execSQL(query, new Object[]{custo, nome, fabricante, socket, slots_m, capacidade_m, velocidade_m, wifi, tipo_memoria});
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inserirProcessador(double custo, String nome, String fabricante, String socket, double frequencia_b, double frequencia_t, int nucleos, int threads, String gpu_integrada, String gpu_nome, String cache_l1, String cache_l2, String cache_l3) {
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "INSERT INTO cpus (custo, nome, fabricante, socket, frequencia_b, frequencia_t, nucleos, threads, gpu_integrada, gpu_nome, cache_l1, cache_l2, cache_l3) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            BancoDados.execSQL(query, new Object[]{custo, nome, fabricante, socket, frequencia_b, frequencia_t, nucleos, threads, gpu_integrada, gpu_nome, cache_l1, cache_l2, cache_l3});
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inserirPlacaVideo(double custo, String nome, String fabricante, String serie, String interface_gpu, String tipo_memoria, int tamanho_memoria, int clock_base) {
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "INSERT INTO gpus (custo, nome, fabricante, serie, interface, tipo_memoria, tamanho_memoria, clock_b) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            BancoDados.execSQL(query, new Object[]{custo, nome, fabricante, serie, interface_gpu, tipo_memoria, tamanho_memoria, clock_base});
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inserirMemoriaPrincipal(double custo, String nome, String fabricante, int capacidade, int velocidade, int modulos, String tipo_mem) {
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "INSERT INTO mems_p (custo, nome, fabricante, capacidade, velocidade, modulos, tipo_mem) VALUES (?, ?, ?, ?, ?, ?, ?)";
            BancoDados.execSQL(query, new Object[]{custo, nome, fabricante, capacidade, velocidade, modulos, tipo_mem});
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inserirMemoriaSecundaria(double custo, String nome, String fabricante, String tipo, int capacidade, String formato, int velocidade_l, int velocidade_e) {
        try {
            BancoDados = openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "INSERT INTO mems_s (custo, nome, fabricante, tipo, capacidade, formato, velocidade_l, velocidade_e) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            BancoDados.execSQL(query, new Object[]{custo, nome, fabricante, tipo, capacidade, formato, velocidade_l, velocidade_e});
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inserirDadosPlacasMae() {
        inserirPlacaMae(790.0, "ASUS Prime B450M-A", "ASUS", "AM4", 4, 64, 3466, "Não","DDR4");
        inserirPlacaMae(1890.0, "MSI B450 TOMAHAWK MAX", "MSI", "AM4", 4, 64, 4133, "Não","DDR4");
        inserirPlacaMae(1900.0, "Gigabyte B450 AORUS ELITE", "Gigabyte", "AM4", 4, 64, 3200, "Não","DDR4");
        inserirPlacaMae(850.0, "ASRock B450M PRO4", "ASRock", "AM4", 4, 64, 3200, "Não","DDR4");
        inserirPlacaMae(580.0, "ASUS ROG Strix B450-F Gaming", "ASUS", "AM4", 4, 64, 3200, "Sim","DDR4");
        inserirPlacaMae(1400.0, "MSI MPG B550 GAMING EDGE WIFI", "MSI", "AM4", 4, 128, 5100, "Sim","DDR4");
        inserirPlacaMae(1745, "Gigabyte B550 AORUS PRO", "Gigabyte", "AM4", 4, 128, 5200, "Sim","DDR4");
        inserirPlacaMae(2100.0, "ASRock B550M Steel Legend", "ASRock", "AM4", 4, 128, 4733, "Sim","DDR4");
        inserirPlacaMae(2200.0, "ASUS ROG Crosshair VIII Hero", "ASUS", "AM4", 4, 128, 4800, "Sim","DDR4");
        inserirPlacaMae(3539.0, "MSI MEG X570 ACE", "MSI", "AM4", 4, 128, 5000, "Sim","DDR4");
        inserirPlacaMae(1500.0, "ASUS Prime Z590-A", "ASUS", "LGA 1200", 4, 128, 4800, "Sim","DDR4");
        inserirPlacaMae(950.0, "ASRock B560M-ITX/ac", "ASRock", "LGA 1200", 4, 64, 3600, "Não","DDR4");
        inserirPlacaMae(850.0, "ASUS PRIME H270-PLUS", "ASUS", "LGA 1151", 4, 64, 3200, "Não","DDR4");
        inserirPlacaMae(1899.0, "MSI X299 TOMAHAWK AC", "MSI", "LGA 2066", 4, 128, 4400, "Sim","DDR4");
        inserirPlacaMae(1299.0, "ASRock X399 Taichi", "ASRock", "TR4", 4, 256, 3600, "Sim","DDR4");
        inserirPlacaMae(800.0, "Corsair Dominator Platinum", "Corsair", "AM4", 4, 64, 2400, "Não", "DDR3");
        inserirPlacaMae(900.0, "G.Skill TridentX Series", "G.Skill", "LGA 1200", 4, 128, 2800, "Sim", "DDR3");
        inserirPlacaMae(700.0, "Kingston HyperX Savage", "Kingston", "LGA 1151", 4, 64, 2133, "Não", "DDR3");
        inserirPlacaMae(1000.0, "Crucial Ballistix Tactical", "Crucial", "AM4", 4, 128, 2666, "Sim", "DDR3");
        inserirPlacaMae(850.0, "ADATA XPG V1.0", "ADATA", "LGA 2066", 4, 128, 2400, "Sim", "DDR3");
        inserirPlacaMae(1500.0, "Corsair Dominator Platinum RGB", "Corsair", "AM4", 4, 64, 3200, "Não", "DDR5");
        inserirPlacaMae(1700.0, "G.Skill TridentZ RGB", "G.Skill", "LGA 1200", 4, 128, 4800, "Sim", "DDR5");
        inserirPlacaMae(1400.0, "Kingston Fury Beast", "Kingston", "LGA 1151", 4, 64, 3600, "Não", "DDR5");
        inserirPlacaMae(2000.0, "Crucial Ballistix Max", "Crucial", "AM4", 4, 128, 5600, "Sim", "DDR5");
        inserirPlacaMae(1850.0, "ADATA XPG Hunter", "ADATA", "LGA 2066", 4, 128, 5000, "Sim", "DDR5");
    }

    public void inserirDadosProcessadores() {
        inserirProcessador(2755.0, "Intel Core i5-11600K", "Intel", "LGA 1200", 3.9, 4.9, 6, 12, "Não", null, "64KB", "256KB", "12MB");
        inserirProcessador(1780.0, "AMD Ryzen 7 5800X", "AMD", "AM4", 3.8, 4.7, 8, 16, "Não", null, "32KB", "128KB", "32MB");
        inserirProcessador(5500.0, "Intel Core i9-11900K", "Intel", "LGA 1200", 3.5, 5.3, 8, 16, "Não", null, "64KB", "256KB", "16MB");
        inserirProcessador(2619.0, "AMD Ryzen 9 5900X", "AMD", "AM4", 3.7, 4.8, 12, 24, "Não", null, "64KB", "256KB", "64MB");
        inserirProcessador(3220.0, "Intel Core i7-11700K", "Intel", "LGA 1200", 3.6, 5.0, 8, 16, "Não", null, "64KB", "256KB", "16MB");
        inserirProcessador(1570.0, "AMD Ryzen 5 5600X", "AMD", "AM4", 3.7, 4.6, 6, 12, "Não", null, "32KB", "128KB", "32MB");
        inserirProcessador(580.0, "Intel Core i3-10100", "Intel", "LGA 1200", 3.6, 4.3, 4, 8, "Sim", "Intel UHD Graphics 630", "64KB", "256KB", "6MB");
        inserirProcessador(590.0, "AMD Ryzen 3 3300X", "AMD", "AM4", 3.8, 4.3, 4, 8, "Não", null, "16KB", "512KB", "16MB");
        inserirProcessador(990.0, "Intel Pentium Gold G6400", "Intel", "LGA 1200", 4.0, 4.4, 2, 4, "Sim", "Intel UHD Graphics 610", "64KB", "512KB", "4MB");
        inserirProcessador(630.0, "AMD Athlon 3000G", "AMD", "AM4", 3.8, 4.2, 2, 4, "Não", null, "192KB", "1MB", "4MB");
        inserirProcessador(1250.0, "Intel Core i7-7700K", "Intel", "LGA 1151", 4.2, 4.5, 4, 8, "Não", null, "64KB", "256KB", "8MB");
        inserirProcessador(2999.0, "Intel Core i9-10980XE", "Intel", "LGA 2066", 3.0, 4.6, 18, 36, "Sim", null, "64KB", "1.375MB", "24.75MB");
        inserirProcessador(3999.0, "AMD Ryzen Threadripper 3970X", "AMD", "TR4", 3.7, 4.5, 32, 64, "Sim", null, "32KB", "128KB", "144MB");



    }

    public void inserirDadosPlacasVideo() {
        inserirPlacaVideo(6000.0, "NVIDIA GeForce RTX 3070", "NVIDIA", "Ampere", "PCIe 4.0 x16", "GDDR6", 8, 1725);
        inserirPlacaVideo(493.0, "AMD Radeon RX 6700 XT", "AMD", "RDNA 2", "PCIe 4.0 x16", "GDDR6", 12, 2581);
        inserirPlacaVideo(13000.0, "NVIDIA GeForce RTX 3080", "NVIDIA", "Ampere", "PCIe 4.0 x16", "GDDR6X", 10, 1710);
        inserirPlacaVideo(1119.0, "AMD Radeon RX 6800", "AMD", "RDNA 2", "PCIe 4.0 x16", "GDDR6", 16, 2105);
        inserirPlacaVideo(3245.0, "NVIDIA GeForce RTX 3060 Ti", "NVIDIA", "Ampere", "PCIe 4.0 x16", "GDDR6", 8, 1665);
        inserirPlacaVideo(4500.0, "AMD Radeon RX 6600 XT", "AMD", "RDNA 2", "PCIe 4.0 x16", "GDDR6", 8, 2359);
        inserirPlacaVideo(6350.0, "NVIDIA GeForce RTX 3090", "NVIDIA", "Ampere", "PCIe 4.0 x16", "GDDR6X", 24, 1695);
        inserirPlacaVideo(18655, "AMD Radeon RX 6900 XT", "AMD", "RDNA 2", "PCIe 4.0 x16", "GDDR6", 16, 2250);
        inserirPlacaVideo(2610.0, "NVIDIA GeForce RTX 3060", "NVIDIA", "Ampere", "PCIe 4.0 x16", "GDDR6", 12, 1777);
        inserirPlacaVideo(3761.0, "AMD Radeon RX 6800 XT", "AMD", "RDNA 2", "PCIe 4.0 x16", "GDDR6", 16, 2250);
    }

    public void inserirDadosMemoriasPrincipais() {
        inserirMemoriaPrincipal(1120.0, "Corsair Vengeance LPX", "Corsair", 16, 3200, 2, "DDR4");
        inserirMemoriaPrincipal(1170.0, "Kingston HyperX Fury", "Kingston", 32, 2666, 2, "DDR4");
        inserirMemoriaPrincipal(3000.0, "G.Skill Ripjaws V", "G.Skill", 64, 3600, 4, "DDR4");
        inserirMemoriaPrincipal(300.0, "Crucial Ballistix", "Crucial", 8, 3000, 1, "DDR4");
        inserirMemoriaPrincipal(599.0, "Team T-Force Vulcan Z", "Team", 16, 3200, 2, "DDR4");
        inserirMemoriaPrincipal(1190.0, "Patriot Viper Steel Series", "Patriot", 32, 4000, 2, "DDR4");
        inserirMemoriaPrincipal(3000.0, "Corsair Dominator Platinum RGB", "Corsair", 64, 3600, 4, "DDR4");
        inserirMemoriaPrincipal(1480.0, "Crucial Ballistix MAX", "Crucial", 32, 4400, 2, "DDR4");
        inserirMemoriaPrincipal(4550.0, "G.Skill Trident Z Neo", "G.Skill", 128, 3600, 4, "DDR4");
        inserirMemoriaPrincipal(400.0, "Patriot Signature Line", "Patriot", 16, 1600, 2, "DDR3");
        inserirMemoriaPrincipal(300.0, "Corsair Vengeance LP", "Corsair", 8, 1333, 2, "DDR3");
        inserirMemoriaPrincipal(600.0, "Crucial Ballistix Tactical", "Crucial", 16, 1600, 4, "DDR3");
        inserirMemoriaPrincipal(850.0, "G.Skill Trident X", "G.Skill", 32, 2133, 4, "DDR3");
        inserirMemoriaPrincipal(2400.0, "Patriot Viper Steel", "Patriot", 32, 6000, 2, "DDR5");
        inserirMemoriaPrincipal(2600.0, "Corsair Dominator Platinum", "Corsair", 64, 6000, 4, "DDR5");
        inserirMemoriaPrincipal(2800.0, "Crucial Ballistix MAX", "Crucial", 32, 6400, 2, "DDR5");
        inserirMemoriaPrincipal(3000.0, "G.Skill Trident Z Neo", "G.Skill", 128, 6400, 4, "DDR5");
        inserirMemoriaPrincipal(3200.0, "Kingston ValueRAM", "Kingston", 4, 4800, 1, "DDR5");
    }

    public void inserirDadosMemoriasSecundarias() {
        inserirMemoriaSecundaria(1119.0, "Corsair MP510", "Corsair", "SSD", 1000, "M.2", 3480, 3000);
        inserirMemoriaSecundaria(920.0, "Samsung 970 EVO Plus", "Samsung", "SSD", 500, "M.2", 3500, 3300);
        inserirMemoriaSecundaria(2180.0, "WD Black SN750", "WD", "SSD", 2000, "M.2", 3430, 2600);
        inserirMemoriaSecundaria(680.0, "Crucial P5", "Crucial", "SSD", 1000, "M.2", 3400, 3000);
        inserirMemoriaSecundaria(500.0, "Kingston A2000", "Kingston", "SSD", 500, "M.2", 2200, 2000);
        inserirMemoriaSecundaria(2000.0, "Sabrent Rocket Q", "Sabrent", "SSD", 2000, "M.2", 3200, 2900);
        inserirMemoriaSecundaria(540.0, "ADATA XPG SX8200 Pro", "ADATA", "SSD", 1000, "M.2", 3500, 3000);
        inserirMemoriaSecundaria(591.0, "Seagate FireCuda 520", "Seagate", "SSD", 500, "M.2", 5000, 2500);
        inserirMemoriaSecundaria(1340.0, "Silicon Power US70", "Silicon Power", "SSD", 2000, "M.2", 5000, 4400);
        inserirMemoriaSecundaria(680.0, "Gigabyte AORUS NVMe Gen4", "Gigabyte", "SSD", 1000, "M.2", 5000, 4400);
    }
}