# Política de Privacidade — Trafego Rotta

**Última atualização:** Outubro de 2026  
**Versão:** 1.0.0 (Lançamento Oficial Google Play)  
**Aplicativo:** Trafego Rotta  
**Jurisdição:** Brasil (em estrita conformidade com a Lei Geral de Proteção de Dados — LGPD, Lei nº 13.709/2018)

---

## 1. Apresentação e Compromisso

O **Trafego Rotta** foi projetado especificamente para motoristas de aplicativos de transporte de passageiros (Uber e 99). Nossa premissa fundamental de arquitetura é o **processamento 100% local no dispositivo do usuário** (*on-device privacy*). 

Acreditamos que suas finanças, seus ganhos e sua rotina de trabalho pertencem exclusivamente a você. Por isso, o Trafego Rotta **não vende, não transfere e não compartilha seus dados com terceiros nem com anunciantes**.

---

## 2. Princípio da Minimização de Dados (LGPD Art. 6º, III)

Coletamos e processamos exclusivamente as informações estritamente necessárias para a finalidade de cálculo financeiro e exibição de métricas operacionais.

### Tabela de Dados e Finalidades

| Categoria do Dado | Dado Específico | Finalidade | Armazenamento | Transmitido Externamente? |
|---|---|---|---|---|
| **Veículo & Custos** | Modelo, consumo médio (km/l), preço do combustível, custos fixos mensais (seguro, IPVA, manutenção). | Calcular custo real por quilômetro (R$/km) e ponto de equilíbrio diário. | Banco SQLite local (`Room`) no seu aparelho. | **Não.** Nunca sai do aparelho. |
| **Metas do Motorista** | Metas diárias, semanais e mensais em reais (R$). | Exibir barra de progresso e ritmo de ganho na jornada. | Banco SQLite local (`Room`) no seu aparelho. | **Não.** Nunca sai do aparelho. |
| **Ofertas de Corridas** | Valor bruto da tarifa (R$), distância total estimada (km) e tempo estimado (min). | Calcular o semáforo em tempo real (R$/km e R$/h) para auxiliar na decisão de aceite. | Memória volátil durante a análise; gravado no histórico de jornada apenas após conclusão ou registro manual. | **Não.** Nunca sai do aparelho. |
| **Passageiros** | **Nome, foto, nota ou endereço exato de partida/destino.** | **NENHUMA.** O Trafego Rotta rejeita e descarta ativamente qualquer informação identificável de passageiro. | **NÃO COLETADO / NÃO ARMAZENADO.** | **Não.** |

---

## 3. Uso da API de Serviços de Acessibilidade (AccessibilityService API)

O Trafego Rotta utiliza a `AccessibilityService API` do Android com uma **finalidade exclusiva, declarada e restrita**:

1. **Leitura Passiva de Tarifas:** Ler os valores numéricos exibidos nas telas de ofertas de corrida dos aplicativos parceiros suportados (Uber e 99), especificamente o valor da tarifa (R$), a distância em km e o tempo estimado.
2. **Cálculo Determinístico Imediato:** Comparar esses valores com as configurações de custo mínimo do motorista para exibir o semáforo visual de rentabilidade (*Vale*, *Neutra* ou *Fraca*) em menos de 1 segundo.
3. **Limites Técnicos Inegociáveis:**
   - O serviço está configurado com `canPerformGestures="false"`.
   - O aplicativo **NÃO realiza cliques**, **NÃO executa toques**, **NÃO aceita**, **NÃO rejeita** e **NÃO cancela** corridas automaticamente.
   - **Toda e qualquer decisão de aceite é exclusivamente humana e manual do motorista.**
   - O serviço de acessibilidade observa unicamente os pacotes dos aplicativos de corrida suportados (`com.ubercab.driver`, `com.taxis99.driver`, `com.taxis99`).
4. **Consentimento Destacado Obrigatório:** Antes de ativar o serviço, o motorista visualiza uma tela de consentimento claro e destacado no app, explicando detalhadamente a finalidade. O usuário pode recusar a qualquer momento e o aplicativo continuará 100% funcional (com lançamentos manuais, metas, jornada e relatórios).

---

## 4. Uso de Serviços em Primeiro Plano (Foreground Service)

O Trafego Rotta utiliza um serviço em primeiro plano declarado como `specialUse` no Android 14+ (API 34+) para manter a **bolha flutuante (overlay de jornada e semáforo)** visível enquanto o motorista está com o aplicativo de GPS ou de corrida aberto.

- Uma notificação persistente é mantida na barra de status do sistema enquanto o copiloto estiver ativo.
- O motorista pode encerrar o serviço e recolher a bolha a qualquer instante com um único toque.

---

## 5. Armazenamento e Segurança dos Dados

- **Armazenamento Local:** Todos os lançamentos, relatórios, histórico de corridas e configurações são salvos exclusivamente no banco de dados local criptografável do sistema operacional Android (`Room Database`).
- **Sem Servidores Centrais de Telemetria:** O aplicativo não executa rastreadores de publicidade de terceiros nem vende perfis comportamentais.
- **Exportação de Dados:** O motorista possui o direito e o recurso nativo no app para exportar seu relatório financeiro completo em formato universal `.csv` a qualquer momento para fins de contabilidade ou declaração MEI.

---

## 6. Direitos do Titular sob a LGPD (Artigo 18)

Como usuário e titular dos dados, você tem pleno direito de:
1. **Confirmar a existência do tratamento:** Garantido pela transparência deste documento e das telas do app.
2. **Acessar e exportar seus dados:** Diretamente na aba de Relatórios, gerando arquivo CSV.
3. **Corrigir dados incompletos ou inexatos:** Na tela de Perfil e Carro a qualquer momento.
4. **Eliminar seus dados:** Ao limpar os dados do aplicativo nas configurações do Android ou desinstalar o app, 100% dos dados são eliminados permanentemente do seu dispositivo.
5. **Revogar o consentimento:** Você pode revogar as permissões de Acessibilidade e Sobreposição de Tela a qualquer momento diretamente nas configurações de privacidade do seu Android.

---

## 7. Dados de Crianças e Adolescentes

O Trafego Rotta destina-se exclusivamente a motoristas profissionais habilitados e maiores de idade. Não coletamos intencionalmente informações de crianças ou adolescentes.

---

## 8. Alterações nesta Política

Podemos atualizar esta Política de Privacidade para refletir melhorias no aplicativo ou mudanças regulatórias. Notificaremos qualquer alteração substancial diretamente no aplicativo antes que ela entre em vigor.

---

## 9. Canal de Comunicação e Encarregado de Proteção de Dados (DPO)

Em caso de dúvidas, solicitações ou esclarecimentos sobre privacidade e tratamento de dados:
- **Responsável:** Gabriel / Equipe Trafego Rotta
- **E-mail de Contato:** privacidade@traffigorotta.app
- **Localização:** São Paulo - SP, Brasil
