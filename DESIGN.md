---
version: alpha
name: Trafego Rotta
description: Copiloto financeiro para motoristas de aplicativo. Preto e branco, tipografia grande para números, cor só quando significa algo.
colors:
  primary: "#0A0A0A"
  on-primary: "#FFFFFF"
  surface: "#FFFFFF"
  surface-variant: "#F4F4F5"
  on-surface: "#0A0A0A"
  on-surface-muted: "#5F5F66"
  outline: "#E4E4E7"
  inverse-surface: "#0A0A0A"
  on-inverse-surface: "#FAFAFA"
  signal-good: "#15803D"
  on-signal-good: "#FFFFFF"
  signal-medium: "#FACC15"
  on-signal-medium: "#0A0A0A"
  signal-bad: "#B91C1C"
  on-signal-bad: "#FFFFFF"
typography:
  display:
    fontFamily: Geist
    fontSize: 56px
    fontWeight: 600
    lineHeight: 1.05
    letterSpacing: -0.03em
    fontFeature: tnum
  headline:
    fontFamily: Geist
    fontSize: 28px
    fontWeight: 600
    lineHeight: 1.15
    letterSpacing: -0.02em
  title:
    fontFamily: Geist
    fontSize: 18px
    fontWeight: 600
    lineHeight: 1.3
  body:
    fontFamily: Geist
    fontSize: 16px
    fontWeight: 400
    lineHeight: 1.5
  body-sm:
    fontFamily: Geist
    fontSize: 14px
    fontWeight: 400
    lineHeight: 1.45
  label:
    fontFamily: Geist
    fontSize: 12px
    fontWeight: 500
    lineHeight: 1.3
    letterSpacing: 0.06em
    fontFeature: tnum
rounded:
  sm: 8px
  md: 16px
  lg: 28px
  full: 9999px
spacing:
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 40px
  gutter: 20px
components:
  button-primary:
    backgroundColor: "{colors.primary}"
    textColor: "{colors.on-primary}"
    typography: "{typography.title}"
    rounded: "{rounded.full}"
    height: 56px
    padding: 16px
  button-secondary:
    backgroundColor: "{colors.surface-variant}"
    textColor: "{colors.on-surface}"
    typography: "{typography.title}"
    rounded: "{rounded.full}"
    height: 56px
    padding: 16px
  card:
    backgroundColor: "{colors.surface-variant}"
    textColor: "{colors.on-surface}"
    typography: "{typography.body}"
    rounded: "{rounded.md}"
    padding: 20px
  stat-hero:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.on-surface}"
    typography: "{typography.display}"
    padding: 24px
  stat-label:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.on-surface-muted}"
    typography: "{typography.label}"
  overlay-bubble:
    backgroundColor: "{colors.inverse-surface}"
    textColor: "{colors.on-inverse-surface}"
    typography: "{typography.title}"
    rounded: "{rounded.full}"
    size: 52px
  overlay-panel:
    backgroundColor: "{colors.inverse-surface}"
    textColor: "{colors.on-inverse-surface}"
    typography: "{typography.headline}"
    rounded: "{rounded.lg}"
    padding: 16px
  signal-good:
    backgroundColor: "{colors.signal-good}"
    textColor: "{colors.on-signal-good}"
    typography: "{typography.title}"
    rounded: "{rounded.full}"
    height: 40px
  signal-medium:
    backgroundColor: "{colors.signal-medium}"
    textColor: "{colors.on-signal-medium}"
    typography: "{typography.title}"
    rounded: "{rounded.full}"
    height: 40px
  signal-bad:
    backgroundColor: "{colors.signal-bad}"
    textColor: "{colors.on-signal-bad}"
    typography: "{typography.title}"
    rounded: "{rounded.full}"
    height: 40px
---

## Overview

Trafego Rotta é um painel de instrumentos, não uma rede social. O motorista olha para o celular por frações de segundo, muitas vezes dirigindo, então a interface precisa responder uma pergunta por tela: "quanto ganhei?", "essa corrida compensa?", "estou no caminho da meta?".

A atmosfera é preto e branco, calma e editorial. Um número grande domina cada tela; todo o resto é secundário e discreto. A única cor fora do preto e branco é o semáforo (verde, amarelo, vermelho), reservado a quem avalia corridas. Cor decorativa não existe neste produto.

Referência de comportamento: apps de avaliação de corrida como o Gigu mostram um semáforo rápido; aqui a diferença é o acabamento visual sóbrio e a hierarquia limpa.

## Colors

A paleta é neutra e de alto contraste. Há dois modos, com os mesmos papéis invertidos.

- **Primary (#0A0A0A):** botões principais, texto, bolha do overlay.
- **Surface (#FFFFFF) e Surface-variant (#F4F4F5):** fundo e cartões no modo claro. No modo escuro, o fundo é #0A0A0A e os cartões ficam em um cinza muito escuro derivado dele.
- **On-surface-muted (#5F5F66):** rótulos, datas, texto de apoio. Nunca para valores monetários.
- **Signal-good / medium / bad:** exclusivos do semáforo e de estados de meta (batida, em risco, perdida). Verde e vermelho foram escurecidos para manter contraste AA com texto branco; o amarelo usa texto preto.
- O app nunca usa gradientes, nem cores de destaque extras, nem ícones coloridos.

## Typography

Uma única família, Geist, com variações de peso e tamanho para criar hierarquia. Números monetários e de tempo usam algarismos tabulares (`tnum`) para não "pularem" quando mudam.

- **Display (56px):** um único valor por tela (ganho do dia, ganho da jornada).
- **Headline (28px):** valores secundários e títulos de seção.
- **Title (18px):** botões e itens de lista.
- **Body (16px) e Body-sm (14px):** texto corrido e apoio.
- **Label (12px, espaçado):** rótulos de estatística em caixa alta, como "GANHO HOJE" ou "R$/KM".

Máximo de três tamanhos visíveis por tela.

## Layout

Coluna única, margem lateral de 20px, ritmo vertical em múltiplos de 8px. Cada tela tem no máximo cinco blocos visíveis sem rolar, e uma única ação primária, posicionada na zona do polegar (parte inferior). Informação secundária vai para uma tela subsidiária ou bottom sheet, nunca ao lado da principal.

Navegação inferior com no máximo quatro destinos: Início, Relatórios, Metas, Perfil. O botão de iniciar/encerrar jornada fica em Início e ocupa a largura toda.

O overlay segue regras próprias: começa como bolha de 52px, encostada na lateral, sem cobrir a área inferior onde apps de corrida costumam colocar o botão de aceitar (o motorista deve confirmar isso no próprio aparelho). A posição é arrastável e lembrada.

## Elevation & Depth

Interface plana. Profundidade vem de contraste de superfície (branco sobre cinza muito claro, ou preto sobre cinza muito escuro), nunca de sombras fortes. Apenas o overlay e bottom sheets podem ter uma sombra sutil, porque flutuam sobre outro app.

## Shapes

Formas arredondadas e consistentes: botões e chips em pílula (full), cartões com 16px, painel do overlay com 28px. Nunca misturar cantos retos e arredondados na mesma tela.

## Components

- **Hero de ganho:** rótulo pequeno em cima, valor em Display embaixo, uma linha de contexto em Body-sm (por exemplo, "meta diária: 68%"). É o primeiro elemento da tela Início.
- **Botão de jornada:** primário, largura total; "Iniciar jornada" quando parada, "Encerrar jornada" quando ativa, sempre com o tempo decorrido ao lado.
- **Cartões de relatório:** no máximo três métricas por cartão. Gráficos simples em preto e cinza; destaque do melhor horário em preto sólido, resto em cinza.
- **Meta:** barra de progresso fina em preto, percentual em Headline, sem cores, exceto quando a meta é batida (verde discreto, só no rótulo).
- **Overlay recolhido:** círculo preto com a logo, sem texto.
- **Overlay expandido:** ganho da jornada em Headline e o semáforo da corrida atual (pílula com cor e uma palavra: "Vale", "Neutra", "Fraca"), mais duas métricas pequenas (R$/km e R$/h). Nada além disso.
- **Semáforo:** sempre cor mais palavra, nunca só cor, para acessibilidade.

## Do's and Don'ts

- Faça: um número dominante por tela; uma ação primária; espaço em branco generoso; rótulos curtos em português do Brasil.
- Faça: revelar o secundário sob demanda (tocar para expandir), com no máximo dois níveis de profundidade.
- Faça: mostrar estados vazios com uma frase e uma ação, não com ilustrações.
- Não faça: gradientes, glassmorphism, ícones coloridos, sombras pesadas, emojis como decoração.
- Não faça: mais de uma cor de destaque; cor fora do semáforo e dos estados de meta.
- Não faça: mais de cinco blocos acima da dobra; tabelas densas; textos de ajuda longos na tela principal.
- Não faça: cobrir com o overlay os controles do app de corrida; o overlay nunca ocupa mais que 30% da altura da tela quando expandido.
- Não faça: animações longas. Movimento é curto (150 a 250ms), com mola leve, e respeita a configuração de reduzir movimento do sistema.
