# Kit de Lançamento Google Play Store — Trafego Rotta

Este documento contém todos os textos, metadados e instruções práticas para preenchimento imediato no **Google Play Console** durante o envio do aplicativo.

---

## 1. Dados Básicos do Aplicativo (Play Store Listing)

### Nome do Aplicativo (App Title)
> **Trafego Rotta: Copiloto Uber 99**  
*(29 caracteres / Limite máximo: 30 caracteres)*

---

### Descrição Breve (Short Description)
> **Copiloto financeiro para motoristas de app: semáforo de corridas, metas e lucro.**  
*(79 caracteres / Limite máximo: 80 caracteres)*

---

### Descrição Completa (Full Description)
*(Texto formatado para a loja, respeitando o limite de 4.000 caracteres, sem exageros de marketing e com total ênfase na segurança e utilidade para o motorista de aplicativo)*

```text
Trafego Rotta é o copiloto financeiro desenvolvido sob medida para motoristas de aplicativos (Uber e 99) no Brasil.

Chega de fazer cálculos matemáticos de cabeça a 60 km/h enquanto dirige. O Rotta calcula instantaneamente o lucro real de cada corrida, controla sua jornada de trabalho e garante que você bata suas metas diárias com segurança e tranquilidade.

■ SEMÁFORO DE CORRIDAS DE RELANCE (MENOS DE 1 SEGUNDO)
Assim que uma oferta de corrida da Uber ou 99 aparece na sua tela, o Rotta compara a proposta com seus custos operacionais reais (combustível, manutenção e custos fixos) e sinaliza em uma bolha discreta:
• VERDE (VALE): O ganho por quilômetro e por hora supera sua margem mínima de lucro.
• AMARELO (NEUTRA): A corrida cobre seus custos, mas fica no limite da sua meta.
• VERMELHO (FRACA): A corrida não compensa o desgaste do carro e combustível.

■ BOLHA FLUTUANTE QUE NÃO ATRAPALHA
• Ocupa apenas 52px na lateral da tela.
• Totalmente arrastável: posicionada fora da área inferior onde você aceita corridas.
• Toque para expandir e ver o lucro estimado da viagem.
• Você escolhe se prefere expansão automática ou manual.

■ CUSTO REAL DO QUILÔMETRO & PARIDADE DE COMBUSTÍVEL
• Descubra exatamente quanto custa rodar 1 km com o seu carro (combustível + seguro + IPVA + pneus + manutenção preventiva).
• Calculadora de Paridade Real (Etanol vs Gasolina): esqueça a velha regra simplista dos 70%. O Rotta calcula a eficiência exata do motor do seu modelo para indicar a bomba mais econômica.

■ CONTROLE DA JORNADA & LUCRO LÍQUIDO
• Inicie sua jornada ao sair de casa e acompanhe em tempo real:
  - Faturamento bruto acumulado.
  - Custo veicular estimado descontado automaticamente por quilômetro rodado.
  - Lucro líquido real que vai para o seu bolso.
  - Média de ganho por hora trabalhada (R$/h).

■ METAS DIÁRIAS, SEMANAIS E MENSAIS
• Defina quanto precisa faturar por dia e acompanhe o ritmo da jornada com barra de progresso visual.
• Saiba a qualquer instante se seu ritmo está adiantado ou se precisa esticar o turno para bater o alvo.

■ RELATÓRIOS DETALHADOS E EXPORTAÇÃO PARA O MEI
• Histórico organizado por dia, semana e mês.
• Gráfico por faixa de horário (06h-09h, 10h-13h, 14h-17h, 17h-20h) para você identificar onde a sua cidade realmente paga mais.
• Exportação do faturamento com um toque em arquivo CSV pronto para a sua contabilidade ou declaração anual MEI.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
SEGURANÇA E PRIVACIDADE INEGOCIÁVEIS (LGPD)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. O ROTTA NUNCA CLICA NEM TOCA NA SUA TELA:
O aplicativo é 100% passivo. Ele NUNCA aceita, recusa ou executa qualquer ação automática no Uber ou 99. A decisão e o toque na tela são sempre seus. Respeitamos integralmente as diretrizes do Google Play e os termos dos aplicativos de corrida.

2. LEITURA RESTRITA POR ACESSIBILIDADE:
O serviço de acessibilidade do Rotta lê única e exclusivamente os números da corrida (valor em R$, distância em km e tempo).

3. ZERO COLETA DE DADOS DE PASSAGEIROS:
O aplicativo NÃO lê, NÃO processa e NÃO armazena nome, foto, nota ou endereço exato de nenhum passageiro.

4. 100% LOCAL NO SEU APARELHO:
Seus ganhos e relatórios financeiros ficam guardados no seu próprio celular. Nenhum dado financeiro seu é vendido ou compartilhado com terceiros.

5. FUNCIONA MESMO SEM ACESSIBILIDADE:
Se você preferir não ativar a leitura de tela, o Rotta continua 100% útil para acompanhamento manual da jornada, calculadora de custos, paridade de combustível e relatórios de metas.

Trafego Rotta: dirija com números claros e leve mais dinheiro para casa.
```

---

## 2. Categorização e Informações de Contato no Play Console

- **Categoria Principal:** Finanças (*Finance*)
- **Categoria Secundária:** Ferramentas (*Tools*) / Automóveis e Veículos (*Auto & Vehicles*)
- **Tags recomendadas:** Motorista de aplicativo, Uber, 99, Finanças, Metas, Controle de Combustível, MEI
- **E-mail de Suporte:** suporte@traffigorotta.app
- **URL da Política de Privacidade:** `https://traffigorotta.app/privacidade` (ou URL hospedada do `PRIVACY_POLICY.md`)
- **Classificação Indicativa (IARC):** Livre / Para todas as idades (sem conteúdo ofensivo, sem compras de lootbox, sem redes sociais abertas).

---

## 3. Respostas para o Questionário de Conteúdo do App

### Segurança dos Dados (Data Safety Section)
- **O app coleta dados do usuário?**
  - Dados financeiros inseridos pelo usuário (valores e gastos) são salvos **apenas localmente** no dispositivo (`Ephemerally / Stored Locally only`).
  - O app **não transfere dados pessoais pela internet para servidores externos**.
  - O app **não compartilha dados com terceiros**.
- **O app coleta localização precisa em segundo plano?**
  - **Não.** A distância é obtida através do hodômetro informado ou dos dados numéricos extraídos da tela da oferta.

### Acessibilidade (Accessibility Declaration)
- Utiliza a resposta oficial do `PLAY_STORE_COMPLIANCE.md`.

---

## 4. Guia de Assets Gráficos para o Play Console

| Asset | Formato | Dimensões | Descrição Visual Sugerida |
|---|---|---|---|
| **Ícone do App** | PNG 32-bit com canal alfa | 512 x 512 px | Fundo preto puro (`#09090B`), monograma geométrico estilizado "R" em traço branco de alta precisão com sutil detalhe no raio da curva. |
| **Imagem de Recursos** *(Feature Graphic)* | JPEG ou PNG 24-bit | 1024 x 500 px | Fundo escuro com estética editorial do `DESIGN.md`: número dominante central ("R$ 38,50"), chip do semáforo "● VALE" e texto conciso: "Trafego Rotta • O copiloto do motorista". |
| **Screenshots (Telefone)** | PNG ou JPEG | 1080 x 2400 px (9:20) | 5 capturas essenciais: <br>1. *Jornada em Andamento* (Lucro em destaque)<br>2. *Semáforo de Ofertas* (Explicando Verde/Amarelo/Vermelho)<br>3. *Relatório por Horário* (Melhores faixas do dia)<br>4. *Paridade de Combustível* (Etanol vs Gasolina real)<br>5. *Metas e MEI* (Exportação para contabilidade) |
