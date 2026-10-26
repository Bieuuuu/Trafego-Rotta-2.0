# Trafego Rotta — instruções para o agente

Leia este arquivo e o `DESIGN.md` antes de escrever qualquer código ou interface. O `DESIGN.md` manda em tudo que é visual; este arquivo manda em escopo, arquitetura e limites.

## O produto

Trafego Rotta é um copiloto financeiro para motoristas de aplicativo (Uber e 99), para Android, com lançamento planejado na Play Store. O público dirige enquanto usa o app: tela de relance, poucos elementos, números grandes.

Funções planejadas:

- Login e perfil do motorista, com dados do carro (modelo, consumo, custos fixos).
- Iniciar e encerrar jornada.
- Relatórios de ganhos por dia, semana e mês, e por horário (quando ganhou mais ou menos).
- Metas diárias, semanais e mensais.
- Recomendações simples ao motorista, com base no histórico dele.
- Overlay flutuante opcional: bolha pequena com a logo; ao tocar, expande e mostra o ganho da jornada atual e um semáforo (verde, amarelo, vermelho) para a corrida que acabou de aparecer. A expansão automática ao surgir uma corrida é uma opção que o usuário liga ou desliga.
- Leitura das corridas exibidas nos apps Uber e 99 durante a jornada (oferecidas, aceitas, canceladas, trajeto alterado, concluídas).

## Limites (inegociáveis)

1. **O app não aceita, recusa nem toca em nada nos apps de corrida.** Nada de clicar, arrastar ou gesto automático em outro app. A decisão é sempre do motorista. Motivos: a política do Google Play proíbe qualquer uso da API de acessibilidade que inicie e execute ações de forma autônoma, e a Uber afirma que ferramentas de automação que agem no lugar do motorista violam seus termos. Não implemente `dispatchGesture`, `performAction(ACTION_CLICK)` nem equivalente sobre apps de terceiros.
2. **Acessibilidade só para leitura.** O `AccessibilityService` serve apenas para ler os campos de uma oferta (valor, distância, tempo) e o estado da corrida. Configure o serviço para observar somente os pacotes dos apps de corrida e sem capacidade de gestos.
3. **Minimização de dados.** Não armazene nome, foto, nota ou endereço exato do passageiro. Guarde apenas valor, distância estimada, tempo estimado, estado e horário. Processe tudo no aparelho. Nenhum dado sai do celular sem consentimento explícito e finalidade clara (LGPD).
4. **Consentimento destacado.** Antes de pedir a permissão de acessibilidade, mostre uma tela própria, separada da política de privacidade, dizendo exatamente quais dados o serviço lê e para quê, com botão de aceitar e de recusar. O app precisa continuar útil (relatórios manuais, metas, jornada) se o usuário recusar.
5. **Overlay não atrapalha.** Posição arrastável, lembrada, fora da área inferior da tela por padrão, no máximo 30% da altura quando expandido.

## Entrega em fases

- **Fase 1 (seguro para a loja):** login, perfil e carro, jornada, lançamento manual de ganhos e despesas, relatórios, metas, overlay com ganhos da jornada.
- **Fase 2:** leitura das ofertas e das corridas pelo serviço de acessibilidade, semáforo no overlay, relatórios automáticos.
- **Fase 3:** recomendações mais ricas. Qualquer ideia de ação automática sobre apps de terceiros fica fora do escopo e só volta com decisão explícita do Gabriel e revisão jurídica.

## Semáforo (regra de negócio)

O motorista configura o mínimo que aceita ganhar por km, por minuto e por hora. Para cada oferta, calcule R$/km, R$/min e R$/h estimados, compare com os mínimos e classifique: verde (atende a todos), amarelo (atende a parte), vermelho (não atende). A resposta precisa aparecer em menos de 1 segundo, porque a oferta fica pouco tempo na tela (cerca de 5 segundos em várias plataformas). Mostre cor mais palavra, nunca só cor. Cálculo determinístico no aparelho; sem IA nesta etapa.

## Arquitetura (padrão proposto, confirmar com o Gabriel)

- Kotlin, Jetpack Compose, Material 3 como base, tema derivado do `DESIGN.md`.
- MVVM com unidirectional data flow, uma `ViewModel` por tela, `StateFlow`.
- Room para dados locais; Hilt para injeção; Navigation Compose, atividade única.
- Módulos sugeridos: `app`, `core:design` (tema e componentes), `core:data`, `feature:journey`, `feature:reports`, `feature:goals`, `feature:profile`, `feature:overlay` (serviço de overlay), `service:reader` (acessibilidade, somente leitura).
- Overlay: `WindowManager` com `TYPE_APPLICATION_OVERLAY`, hospedando uma `ComposeView` dentro de um foreground service. No Android 14 ou superior, declare o tipo de foreground service correto e a descrição exigida no Play Console.
- Tokens: gere o tema a partir do `DESIGN.md`. Rode `npx @google/design.md lint DESIGN.md` após qualquer mudança nele.

## Como trabalhar a interface

- Antes de entregar cada tela, faça uma auditoria contra o `DESIGN.md`: um número dominante, uma ação primária, no máximo cinco blocos acima da dobra, nenhuma cor fora do semáforo e dos estados de meta, nenhum gradiente.
- Se estiver em dúvida entre mostrar e esconder, esconda e ofereça um toque para expandir.
- Conteúdo em português do Brasil, textos curtos, sem jargão.
- Não invente telas, funções ou métricas fora deste arquivo. Se algo faltar, pergunte.

## Referências para consultar

- Estrutura Android: `android/nowinandroid` e `android/architecture-samples` no GitHub.
- Telas e componentes: `android/compose-samples` (Jetsnack, Jetcaster, Reply) e o catálogo do Material 3.
- Política de acessibilidade do Google Play: https://support.google.com/googleplay/android-developer/answer/10964491
- Captura de tela, se um dia for necessária: https://developer.android.com/media/grow/media-projection
- Exemplos de overlay com acessibilidade (para estudar, sem copiar a parte de cliques): `eci99/Clicker`, `robNice/NavXS`.
- Princípios: *Laws of UX*, de Jon Yablonski; *Refactoring UI*, de Adam Wathan e Steve Schoger; revisão progressiva (progressive disclosure), do Nielsen Norman Group.
