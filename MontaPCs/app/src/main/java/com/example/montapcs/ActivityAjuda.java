package com.example.montapcs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityAjuda extends AppCompatActivity {

    private ImageView button_pc, button_placa_m, button_cpu, button_gpu, button_memoria_p, button_memoria_s;

    private int[] estadosQuadrados = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);

        getWindow().setStatusBarColor(Color.parseColor("#A4E9FF"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAjuda.this, ActivityComputador.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        // Array para controle dos buttons
        List<Integer> indices = new ArrayList<>(Arrays.asList(1, 0, 0, 0, 0, 0));

        TextView textView = findViewById(R.id.text_view);

        // Texto HTML a ser Exibido
        String htmlText = "<h2>Título</h2><p>TEXTO HTML.</p>";

        textView.setText(Html.fromHtml(htmlText));

        List<ImageView> listaQuadros = new ArrayList<>();

        button_pc = findViewById(R.id.quadro1);
        button_placa_m = findViewById(R.id.quadro2);
        button_cpu = findViewById(R.id.quadro3);
        button_gpu = findViewById(R.id.quadro4);
        button_memoria_p = findViewById(R.id.quadro5);
        button_memoria_s = findViewById(R.id.quadro6);

        listaQuadros.add(button_pc);
        listaQuadros.add(button_placa_m);
        listaQuadros.add(button_cpu);
        listaQuadros.add(button_gpu);
        listaQuadros.add(button_memoria_p);
        listaQuadros.add(button_memoria_s);

        button_pc.post(new Runnable() {
            @Override
            public void run() {
                button_pc.performClick();
            }
        });

        button_pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetIndices(indices);
                indices.set(0,1);
                changeBackgroundColor(listaQuadros,indices,listaQuadros.get(0));
                String htmlText = "<h2>Como Montar um Computador?</h2>\n" +
                        "\n" +
                        "<p>Para montar um computador, é necessário encontrar as peças ideais para atender suas necessidades, tais como:</p>\n" +
                        "<ul>\n" +
                        "  <li><b>Placa Mãe</b></li>\n" +
                        "  <li><b>Processador (CPU)</b></li>\n" +
                        "  <li><b>Placa de vídeo</b></li>\n" +
                        "  <li><b>Memória Primária RAM</b></li>\n" +
                        "  <li><b>Memória Secundária (SSD/HD)</b></li>\n" +
                        "</ul>\n" +
                        "\n" +
                        "<p>Antes de começar a escolher as peças, é importante definir o propósito do computador. Abaixo estão diversas atividades, desde as mais leves até as mais pesadas, que podem ajudar na definição:</p>\n" +
                        "\n" +
                        "<h2>Atividades Leves:</h2>\n" +
                        "<p>Essas tarefas são relativamente simples e não exigem muitos recursos do computador. Elas incluem:</p>\n" +
                        "<ul>\n" +
                        "  <li><b>Navegação na Web:</b> Abrir sites, verificar e-mails, redes sociais.</li>\n" +
                        "  <li><b>Processamento de Texto:</b> Usar aplicativos como o Microsoft Word ou o Google Docs para escrever documentos.</li>\n" +
                        "  <li><b>Visualização de Mídia:</b> Assistir a vídeos, ouvir música, visualizar imagens.</li>\n" +
                        "</ul>\n" +
                        "\n" +
                        "<h2>Atividades Medianas:</h2>\n" +
                        "<p>Essas tarefas são um pouco mais exigentes e podem beneficiar-se de um computador com especificações intermediárias. Elas incluem:</p>\n" +
                        "<ul>\n" +
                        "  <li><b>Edição de Fotos:</b> Usar softwares como o Adobe Photoshop para retocar imagens.</li>\n" +
                        "  <li><b>Aplicativos de Produtividade:</b> Trabalhar com planilhas complexas, apresentações ou gerenciamento de projetos.</li>\n" +
                        "  <li><b>Programação e Desenvolvimento:</b> Escrever código, compilar e testar programas.</li>\n" +
                        "</ul>\n" +
                        "\n" +
                        "<h2>Atividades Pesadas:</h2>\n" +
                        "<p>Essas tarefas requerem um hardware mais robusto e são mais intensivas em recursos. Elas incluem:</p>\n" +
                        "<ul>\n" +
                        "  <li><b>Edição de Vídeo:</b> Usar softwares como o Adobe Premiere Pro ou DaVinci Resolve para criar e editar vídeos.</li>\n" +
                        "  <li><b>Design Gráfico Avançado:</b> Trabalhar com ilustrações vetoriais, animações ou modelagem 3D.</li>\n" +
                        "  <li><b>Simulação e Renderização:</b> Renderizar cenas 3D, simular física ou executar cálculos complexos.</li>\n" +
                        "</ul>\n" +
                        "\n" +
                        "<h2>Atividades Muito Pesadas:</h2>\n" +
                        "<p>Essas tarefas são as mais exigentes e geralmente são realizadas por profissionais em áreas específicas. Elas incluem:</p>\n" +
                        "<ul>\n" +
                        "  <li><b>Renderização 3D Profissional:</b> Usar softwares como o Autodesk Maya ou o Cinema 4D para criar animações e efeitos visuais.</li>\n" +
                        "  <li><b>Simulações Científicas:</b> Executar simulações complexas em áreas como física, química ou engenharia.</li>\n" +
                        "  <li><b>Jogos de Última Geração:</b> Jogar títulos AAA com gráficos avançados e alta demanda de recursos.</li>\n" +
                        "</ul>";

                textView.setText(Html.fromHtml(htmlText));
            }
        });

        button_placa_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetIndices(indices);
                indices.set(1,1);
                changeBackgroundColor(listaQuadros,indices,listaQuadros.get(1));
                String htmlText = "<h2>Placa-Mãe</h2>\n" +
                        "\n" +
                        "<p>A placa-mãe é o componente central de um computador, responsável por conectar e interligar todos os outros componentes do sistema. A Placa mãe define o teto de potência do seu computador. Ao escolher uma placa-mãe, é importante considerar as seguintes informações:</p>\n" +
                        "\n" +
                        "<h3>Socket</h3>\n" +
                        "<p>O socket (ou soquete) é um conector físico na placa-mãe projetado para receber o processador (CPU). Cada tipo de processador tem um socket específico. O socket permite que a CPU se conecte à placa-mãe e troque informações com outros componentes.</p>\n" +
                        "\n" +
                        "<h3>Slots de Memória</h3>\n" +
                        "<p>Os slots de memória são conectores na placa-mãe onde você insere os módulos de memória RAM. Esses slots permitem expandir a capacidade de memória do sistema, melhorando o desempenho e a capacidade multitarefa do computador.</p>\n" +
                        "\n" +
                        "<h3>Capacidade de Memória</h3>\n" +
                        "<p>A capacidade de memória refere-se à quantidade total de RAM que a placa-mãe pode suportar. É importante verificar a capacidade máxima suportada pela placa-mãe ao escolher os módulos de memória.</p>\n" +
                        "\n" +
                        "<h3>Velocidade Máxima de Memória</h3>\n" +
                        "<p>A velocidade máxima de memória é a taxa de transferência de dados entre a RAM e outros componentes. É importante escolher módulos de memória com velocidades compatíveis com a placa-mãe para garantir um desempenho ideal do sistema.</p>\n" +
                        "\n" +
                        "<h3>Tipo de Memória RAM</h3>\n" +
                        "<p>Existem diferentes tipos de memória RAM, como DDR4 e DDR5. Cada tipo é compatível com módulos específicos. Memórias DDR4 são as mais comuns atualmente, já o DDR5 se trata de modelos mais recentes.</p>\n" +
                        "\n" +
                        "<h3>Módulo Wi-Fi Integrado</h3>\n" +
                        "<p>Algumas placas-mãe possuem um módulo Wi-Fi integrado, permitindo conexão sem fio à rede. Isso é útil para computadores que não possuem uma placa Wi-Fi separada, oferecendo maior conveniência e praticidade. Caso a placa mãe não tenha Wi-Fi integrado, é necessário conectar o computador a um cabo de rede ou comprar uma placa Wi-Fi separada.</p>";
                textView.setText(Html.fromHtml(htmlText));
            }
        });

        button_cpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetIndices(indices);
                indices.set(2,1);
                changeBackgroundColor(listaQuadros,indices,listaQuadros.get(2));
                String htmlText = "<h2>Processador (CPU)</h2>\n" +
                        "<p>O processador é o cérebro de um computador, encarregado de executar cálculos, processar dados e realizar as tarefas solicitadas pelo usuário. Sua eficiência é crucial para o desempenho geral da máquina, impactando diretamente na velocidade de abertura de aplicativos, navegação na web e jogos.</p>\n" +
                        "\n" +
                        "<h2>Socket</h2>\n" +
                        "<p>O socket na placa-mãe é o encaixe específico para o processador, assegurando a compatibilidade entre os componentes. Existem duas principais fabricantes de processadores, AMD e Intel. A AMD geralmente oferece melhor desempenho em multitarefa e um melhor custo-benefício, enquanto a Intel é conhecida por seu desempenho superior em tarefas pesadas e durabilidade.</p>\n" +
                        "\n" +
                        "<h2>Frequência Base</h2>\n" +
                        "<p>A frequência base, medida em GHz, indica a velocidade padrão do processador em ciclos de clock por segundo.</p>\n" +
                        "\n" +
                        "<h2>Núcleos do Processador</h2>\n" +
                        "<p>Os núcleos do processador são unidades de processamento independentes, como quad-core ou octa-core, possibilitando uma multitarefa eficiente.</p>\n" +
                        "\n" +
                        "<h2>Threads</h2>\n" +
                        "<p>Threads são \"subnúcleos\" virtuais dentro dos núcleos físicos, permitindo a execução simultânea de várias tarefas.</p>\n" +
                        "\n" +
                        "<h2>GPU Integrada</h2>\n" +
                        "<p>Alguns processadores possuem uma GPU integrada para processamento gráfico, eliminando a necessidade de uma placa de vídeo dedicada. Entretanto são limitadas comparadas a placas de video dedicadas, mas você pode ter as duas ao mesmo tempo.</p>\n" +
                        "\n" +
                        "<h2>Cache</h2>\n" +
                        "<p>O cache é uma memória rápida dentro do processador que armazena dados frequentemente acessados, acelerando o acesso e melhorando o desempenho.</p>";
                textView.setText(Html.fromHtml(htmlText));
            }
        });

        button_gpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetIndices(indices);
                indices.set(3,1);
                changeBackgroundColor(listaQuadros,indices,listaQuadros.get(3));
                String htmlText = "<h2>Placa de Video (GPU)</h2>\n" +
                        "<p>A laca de Video é responsável pelo processamento de dados gráficos em um computador. Ela desempenha um papel fundamental na renderização de gráficos 2D e 3D, bem como no processamento de vídeo e imagem. A GPU não é obrigatória caso o processador tenha placa de vídeo integrada, entretanto isso pode limitar consideravelmente o computador em algumas atividades.</p>\n" +
                        "\n" +
                        "<h3>Interface</h3>\n" +
                        "<p>A interface da GPU é o canal de comunicação entre a GPU e outros componentes do sistema, como a placa-mãe e a CPU. As interfaces mais comuns são PCI Express (PCIe) e NVLink.</p>\n" +
                        "\n" +
                        "<h3>Tipo de Memória</h3>\n" +
                        "<p>O tipo de memória da GPU é crucial para o desempenho e a capacidade de processamento gráfico. Os tipos de memória mais comuns são GDDR6 e GDDR6X, conhecidos por sua alta largura de banda e baixa latência.</p>\n" +
                        "\n" +
                        "<h3>Tamanho da Memória</h3>\n" +
                        "<p>O tamanho da memória da GPU determina a quantidade de dados que a GPU pode armazenar temporariamente para processamento. GPUs modernas podem ter desde alguns gigabytes até dezenas de gigabytes de memória dedicada.</p>\n" +
                        "\n" +
                        "<h3>Clock Base</h3>\n" +
                        "<p>O clock base é a velocidade padrão na qual a GPU opera. É medido em megahertz (MHz) e representa a taxa de ciclos de clock por segundo.</p>";
                textView.setText(Html.fromHtml(htmlText));
            }
        });

        button_memoria_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetIndices(indices);
                indices.set(4,1);
                changeBackgroundColor(listaQuadros,indices,listaQuadros.get(4));
                String htmlText = "<h2>Memória Primária (RAM)</h2>\n" +
                        "<p>A memória primária, também conhecida como memória de acesso aleatório (RAM), desempenha um papel essencial no funcionamento de um computador. Ela armazena temporariamente dados e instruções que estão sendo processados ativamente pela CPU, permitindo um acesso rápido e eficiente.</p>\n" +
                        "\n" +
                        "<h3>Capacidade</h3>\n" +
                        "<p>A capacidade da memória primária refere-se à quantidade total de dados que ela pode armazenar. Ela é medida em gigabytes (GB) e determina a quantidade de programas e arquivos que o computador pode manipular simultaneamente sem precisar acessar o disco rígido.</p>\n" +
                        "\n" +
                        "<h3>Velocidade</h3>\n" +
                        "<p>A velocidade da memória primária, muitas vezes referida como frequência ou taxa de transferência, determina a rapidez com que os dados podem ser lidos e gravados na memória. Ela é medida em megahertz (MHz) ou gigabytes por segundo (GB/s) e influencia diretamente o desempenho geral do sistema.</p>\n" +
                        "\n" +
                        "<h3>Módulos</h3>\n" +
                        "<p>A memória primária é frequentemente organizada em módulos, que são placas de circuito integrado instaladas nos slots de memória da placa-mãe.Eles podem ser facilmente substituídos ou atualizados para aumentar a capacidade do sistema. </p>\n" +
                        "\n" +
                        "<h3>Tipo de Memória</h3>\n" +
                        "<p>Existem vários tipos de memória primária disponíveis, sendo os mais comuns o DDR4 e o DDR5. O tipo de memória influencia na largura de banda, na latência e na eficiência energética do sistema, sendo importante escolher um tipo compatível com a placa-mãe e o processador do computador.</p>\n";
                textView.setText(Html.fromHtml(htmlText));
            }
        });

        button_memoria_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetIndices(indices);
                indices.set(5,1);
                changeBackgroundColor(listaQuadros, indices,listaQuadros.get(5));
                String htmlText = "<h2>Memória Secundária (HD/SSD)</h2>\n" +
                        "<p>A memória secundária, também conhecida como armazenamento de massa, é um tipo de memória permanente usada para armazenar dados de forma não volátil em um computador. Ao contrário da memória primária (RAM), que é volátil e perde seus dados quando o computador é desligado, a memória secundária retém as informações mesmo quando a energia é desligada.</p>\n" +
                        "\n" +
                        "<h3>Tipo</h3>\n" +
                        "<p>Existem vários tipos de memória secundária, sendo os mais comuns os discos rígidos (HDD), as unidades de estado sólido (SSD), o SSD é superior em velocidade e em Segurança, costuma ser a melhor opção para quando não se precisa armazenar grandes quantidades de arquivos, porém o custo é superior ao de HDD ao aumentar a quantidade de memória</p>\n" +
                        "\n" +
                        "<h3>Capacidade</h3>\n" +
                        "<p>A capacidade da memória secundária refere-se à quantidade total de dados que ela pode armazenar. Ela é medida em gigabytes (GB) ou terabytes (TB) e varia de acordo com o tipo e o modelo do dispositivo de armazenamento.</p>\n" +
                        "\n" +
                        "<h3>Formato</h3>\n" +
                        "<p>O formato da memória secundária pode variar dependendo do tipo de dispositivo de armazenamento. Alguns dos formatos mais comuns incluem:</p>\n" +
                        "<ul>\n" +
                        "  <li>Discos Rígidos (HDD): Geralmente têm um formato de 3,5 polegadas para desktops e 2,5 polegadas para laptops.</li>\n" +
                        "  <li>Unidades de Estado Sólido (SSD): Podem ter diferentes formatos, incluindo 2,5 polegadas, M.2 e mSATA.</li>\n" +
                        "  <li>Outros formatos específicos para dispositivos externos, como pen drives e discos externos.</li>\n" +
                        "</ul>\n" +
                        "\n" +
                        "<h3>Velocidade de Leitura</h3>\n" +
                        "<p>A velocidade de leitura da memória secundária indica a rapidez com que os dados podem ser lidos do dispositivo de armazenamento. Ela é medida em megabytes por segundo (MB/s) e varia de acordo com o tipo e a tecnologia do dispositivo.</p>\n" +
                        "\n" +
                        "<h3>Velocidade de Escrita</h3>\n" +
                        "<p>A velocidade de escrita da memória secundária indica a rapidez com que os dados podem ser gravados no dispositivo de armazenamento. Assim como a velocidade de leitura, ela é medida em megabytes por segundo (MB/s) e depende do tipo e da tecnologia do dispositivo.</p>\n";
                textView.setText(Html.fromHtml(htmlText));
            }
        });
    }

    private void changeBackgroundColor(List<ImageView> listaQuadros, List<Integer> indices, ImageView quadrado) {
        for (int i = 0; i < indices.size(); i++) {
            if (indices.get(i) == 1) {
                int corAzul = ContextCompat.getColor(this, R.color.color_3);
                listaQuadros.get(i).setBackgroundColor(corAzul);
            } else {
                int corAzulClaro = ContextCompat.getColor(this, R.color.color_2);
                listaQuadros.get(i).setBackgroundColor(corAzulClaro);
            }
        }
        Log.d("AjudaActivity", "Função changeBackgroundColor() acionada");
    }

    private void resetIndices(List<Integer> indices) {
        for (int i = 0; i < indices.size(); i++) {
            indices.set(i, 0);
        }
    }
}