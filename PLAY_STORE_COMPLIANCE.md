# Dossiê de Conformidade Google Play Store & LGPD — Trafego Rotta

Este documento contém os textos exatos e as diretrizes necessárias para a submissão do aplicativo **Trafego Rotta** no **Google Play Console**, cumprindo integralmente as políticas do Google Play para o uso da **API de Acessibilidade** e **Foreground Services**.

---

## 1. Declaração da API de Acessibilidade (Play Console)

No Google Play Console, na seção **Conteúdo do app > Política de acessibilidade**, preencha os seguintes campos com as respostas oficiais abaixo:

### Pergunta 1: Seu app usa a AccessibilityService API?
> **Resposta:** Sim.

### Pergunta 2: Para qual finalidade o app usa a AccessibilityService API?
> **Seleção:** Ler conteúdo da tela para fornecer assistência e cálculo operacional a motoristas profissionais.

### Pergunta 3: Descrição detalhada da necessidade (Texto para os revisores da Google Play)
> *"O Trafego Rotta é um copiloto financeiro para motoristas de aplicativos de transporte (Uber e 99) no Brasil. O serviço de acessibilidade é utilizado **estritamente em modo de leitura** para detectar os valores numéricos de ofertas de corridas exibidas na tela (valor bruto da tarifa em R$, distância em km e tempo estimado em minutos).*  
>  
> *O app calcula instantaneamente se a corrida atende aos custos operacionais mínimos definidos pelo motorista (R$/km e R$/h), exibindo um semáforo de relance (Vale, Neutra, Fraca).*  
>  
> *O serviço é 100% passivo: ele **NÃO** realiza gestos (`canPerformGestures="false"`), **NÃO** clica, **NÃO** aceita, **NÃO** recusa e **NÃO** interage com nenhum elemento dos apps parceiros. A decisão de aceite é 100% humana do motorista. Além disso, nenhuma informação de passageiro (nome, foto, nota ou endereço exato) é lida ou armazenada, cumprindo a minimização de dados da LGPD."*

---

## 2. Roteiro do Vídeo de Demonstração Exigido pelo Google Play

O Google exige um link do YouTube (não listado) demonstrando a tela de consentimento e o uso do serviço. Grave o vídeo seguindo esta ordem exata:

1. **Abertura do App:** Mostre a tela de Consentimento Destacado (*Prominent Disclosure*) sendo exibida **antes** de abrir as configurações do Android.
2. **Exibição do Termo:** Destaque a frase: *"O Rotta apenas lê os números da oferta. O app nunca aceita nem recusa nada por você."*
3. **Ativação no Android:** Toque em "Concordar e Ativar", o app abre as Configurações de Acessibilidade do Android. Ligue o "Rotta Copiloto".
4. **Demonstração em Ação:** Abra uma tela de simulação ou corrida da Uber/99. Mostre a bolha flutuante exibindo o Semáforo (Verde "Vale").
5. **Decisão Manual:** Destaque o dedo do motorista tocando manualmente na tela da Uber para aceitar a corrida (provando que não há clique autônomo).

---

## 3. Declaração de Foreground Service (Android 14+)

No Android 14 (API 34+), o uso de serviços em primeiro plano requer a declaração do tipo de serviço.

- **Tipo declarativo no Manifest:** `specialUse`
- **Subtipo:** `Copiloto de relance e overlay de ganhos da jornada e semáforo para motoristas de app`
- **Justificativa para a Play Store:**
  > *"O serviço em primeiro plano mantém a bolha flutuante e o monitoramento da jornada enquanto o motorista utiliza o aplicativo de navegação (Google Maps/Waze) ou o aplicativo de corridas parceiro, exibindo de relance o lucro acumulado da jornada sem sobrecarregar a visão do motorista."*

---

## 4. Minimização de Dados e LGPD (Lei Geral de Proteção de Dados)

| Dado | Lido? | Armazenado? | Sai do Aparelho? |
|---|---|---|---|
| Nome do Passageiro | ❌ NÃO | ❌ NÃO | ❌ NÃO |
| Foto do Passageiro | ❌ NÃO | ❌ NÃO | ❌ NÃO |
| Endereço do Passageiro | ❌ NÃO | ❌ NÃO | ❌ NÃO |
| Nota do Passageiro | ❌ NÃO | ❌ NÃO | ❌ NÃO |
| Valor Bruto da Corrida | ✅ Sim | ✅ Sim (Histórico) | ❌ NÃO (100% no aparelho) |
| Distância Total em km | ✅ Sim | ✅ Sim (Histórico) | ❌ NÃO (100% no aparelho) |
| Tempo Estimado em min | ✅ Sim | ✅ Sim (Histórico) | ❌ NÃO (100% no aparelho) |

---

## 5. Checklist Técnico Pré-Lançamento

- [x] `canPerformGestures="false"` em `rotta_accessibility_service_config.xml`.
- [x] Zero uso de `dispatchGesture`, `performAction(ACTION_CLICK)`.
- [x] Tela própria e destacada de consentimento (`ProminentDisclosureScreen.kt`).
- [x] App continua 100% funcional se o usuário recusar acessibilidade (jornada, lançamentos manuais, metas e relatórios).
- [x] ProGuard configurado (`app/proguard-rules.pro`).
- [x] Semáforo determinístico com cor + palavra (`Vale`, `Neutra`, `Fraca`).
- [x] Bolha flutuante ocupa menos de 30% da altura da tela e é arrastável.
- [x] Notificação persistente no canal de primeiro plano do Android.
- [x] Bloco `signingConfigs` e `isMinifyEnabled = true` configurados em `app/build.gradle.kts`.
- [x] Proteção de credenciais (`.gitignore` bloqueando `*.keystore` e `keystore.properties`).
