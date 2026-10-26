import React, { useState } from 'react';
import { tokens } from './tokens';

export function PublicPrivacyPolicy({ onBack }: { onBack?: () => void }) {
  const [copied, setCopied] = useState(false);

  const handleCopyLink = () => {
    navigator.clipboard.writeText(window.location.href);
    setCopied(true);
    setTimeout(() => setCopied(false), 2500);
  };

  return (
    <div
      style={{
        backgroundColor: '#09090B',
        color: '#FAFAFA',
        minHeight: '100vh',
        fontFamily: 'Geist, -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif'
      }}
      className="py-10 px-4 sm:px-8"
    >
      <div className="max-w-3xl mx-auto space-y-8">
        {/* Top Navigation */}
        <div className="flex items-center justify-between pb-6 border-b border-zinc-800">
          <div className="flex items-center gap-3">
            <div className="w-9 h-9 rounded-full bg-white text-black font-bold flex items-center justify-center text-base">
              R
            </div>
            <div>
              <div className="text-[11px] font-mono tracking-widest text-zinc-400 uppercase">
                TRAFEGO ROTTA • DOCUMENTO OFICIAL
              </div>
              <div className="font-semibold text-lg">Política de Privacidade & LGPD</div>
            </div>
          </div>

          <div className="flex items-center gap-2">
            <button
              onClick={handleCopyLink}
              style={{ borderRadius: tokens.rounded.full }}
              className="px-3.5 py-1.5 text-xs font-medium border border-zinc-700 bg-zinc-900 text-zinc-200 hover:bg-zinc-800 transition cursor-pointer"
            >
              {copied ? '✓ Link Copiado' : 'Copiar Link'}
            </button>
            {onBack && (
              <button
                onClick={onBack}
                style={{ borderRadius: tokens.rounded.full }}
                className="px-3.5 py-1.5 text-xs font-semibold bg-white text-black hover:opacity-90 transition cursor-pointer"
              >
                Voltar ao App
              </button>
            )}
          </div>
        </div>

        {/* Hero Card */}
        <div className="p-6 rounded-xl border border-zinc-800 bg-zinc-950 space-y-3">
          <div className="flex justify-between items-start">
            <span className="text-[11px] font-mono uppercase tracking-widest text-zinc-400">
              CONFORMIDADE JURÍDICA BRASIL • LEI Nº 13.709/2018
            </span>
            <span className="text-xs font-mono font-semibold px-2 py-0.5 rounded bg-emerald-950/80 text-emerald-400 border border-emerald-800">
              VERSÃO 1.0.0 • VIGENTE
            </span>
          </div>

          <h1 className="text-3xl font-bold tracking-tight">
            Privacidade e Processamento 100% no Aparelho
          </h1>

          <p className="text-zinc-400 text-sm leading-relaxed">
            O <strong>Trafego Rotta</strong> foi arquitetado sob o princípio da privacidade por padrão (<em className="text-zinc-300">Privacy by Design</em>). Seus dados financeiros, hodômetros e rotina de trabalho são processados e armazenados <strong>exclusivamente dentro do seu celular</strong>. Não vendemos, não transferimos e não compartilhamos suas informações com terceiros ou redes de publicidade.
          </p>
        </div>

        {/* Seção 1: Minimização de Dados */}
        <div className="p-6 rounded-xl border border-zinc-800 bg-zinc-950/70 space-y-4">
          <h2 className="text-base font-mono uppercase tracking-wider text-zinc-300 font-semibold">
            1. Princípio da Minimização de Dados (LGPD Art. 6º, III)
          </h2>
          <p className="text-sm text-zinc-400">
            Coletamos apenas o estritamente necessário para calcular seus custos reais e o semáforo de ofertas:
          </p>

          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs border border-zinc-800 rounded">
              <thead className="bg-zinc-900 text-zinc-300 uppercase font-mono">
                <tr>
                  <th className="p-3 border-b border-zinc-800">Dado</th>
                  <th className="p-3 border-b border-zinc-800">Finalidade</th>
                  <th className="p-3 border-b border-zinc-800">Local de Guarda</th>
                  <th className="p-3 border-b border-zinc-800">Sai do Aparelho?</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-zinc-800/60 text-zinc-300">
                <tr>
                  <td className="p-3 font-medium">Veículo & Consumo</td>
                  <td className="p-3 text-zinc-400">Calcular custo por km e paridade etanol/gasolina</td>
                  <td className="p-3 font-mono text-[11px]">SQLite local (Room)</td>
                  <td className="p-3 text-emerald-400 font-semibold">NÃO</td>
                </tr>
                <tr>
                  <td className="p-3 font-medium">Metas Diárias/Semanais</td>
                  <td className="p-3 text-zinc-400">Exibir progresso da jornada</td>
                  <td className="p-3 font-mono text-[11px]">SQLite local (Room)</td>
                  <td className="p-3 text-emerald-400 font-semibold">NÃO</td>
                </tr>
                <tr>
                  <td className="p-3 font-medium">Tarifa, km e tempo da oferta</td>
                  <td className="p-3 text-zinc-400">Cálculo determinístico do semáforo (&lt; 1s)</td>
                  <td className="p-3 font-mono text-[11px]">Memória volátil / Histórico</td>
                  <td className="p-3 text-emerald-400 font-semibold">NÃO</td>
                </tr>
                <tr className="bg-red-950/20">
                  <td className="p-3 font-medium text-red-300">Passageiro (Nome, Foto, Endereço)</td>
                  <td className="p-3 text-zinc-400">Nenhuma finalidade</td>
                  <td className="p-3 text-red-400 font-mono text-[11px]">NÃO COLETADO</td>
                  <td className="p-3 text-red-400 font-semibold">NÃO</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        {/* Seção 2: Acessibilidade */}
        <div className="p-6 rounded-xl border border-zinc-800 bg-zinc-950/70 space-y-3">
          <h2 className="text-base font-mono uppercase tracking-wider text-zinc-300 font-semibold">
            2. Uso Restrito da API de Acessibilidade (AccessibilityService API)
          </h2>
          <div className="text-sm text-zinc-400 space-y-2 leading-relaxed">
            <p>
              O aplicativo utiliza a <strong className="text-zinc-200">AccessibilityService API</strong> do Android com finalidade exclusivamente passiva de leitura operacional:
            </p>
            <ul className="list-disc pl-5 space-y-1.5 text-zinc-300">
              <li>
                <strong>Somente Leitura:</strong> lê unicamente os números da corrida (valor em R$, km e minutos) ao surgir a notificação ou cartão de oferta dos aplicativos suportados (Uber e 99).
              </li>
              <li>
                <strong>Zero Ações Autônomas:</strong> o serviço é configurado com <code className="text-zinc-200 bg-zinc-900 px-1.5 py-0.5 rounded font-mono">canPerformGestures="false"</code>. O Rotta <strong>nunca clica, nunca toca, nunca aceita e nunca recusa corridas</strong>. Toda decisão é 100% humana do motorista.
              </li>
              <li>
                <strong>Consentimento Prévio e Destacado:</strong> o aplicativo exibe uma tela explicativa obrigatória antes de solicitar a permissão do sistema operacional.
              </li>
              <li>
                <strong>Independência:</strong> o motorista pode recusar a permissão a qualquer momento e o aplicativo permanece 100% funcional (com lançamentos manuais, metas, jornada e paridade de combustível).
              </li>
            </ul>
          </div>
        </div>

        {/* Seção 3: Foreground Service */}
        <div className="p-6 rounded-xl border border-zinc-800 bg-zinc-950/70 space-y-3">
          <h2 className="text-base font-mono uppercase tracking-wider text-zinc-300 font-semibold">
            3. Serviços em Primeiro Plano (Foreground Service Android 14+)
          </h2>
          <p className="text-sm text-zinc-400 leading-relaxed">
            O aplicativo executa um serviço em primeiro plano declarado como <code className="text-zinc-200 bg-zinc-900 px-1.5 py-0.5 rounded font-mono">specialUse</code> para manter a bolha flutuante discreta (52px) visível sobre os apps de navegação (Google Maps/Waze) durante a jornada de trabalho, permitindo consulta de relance do lucro acumulado. Uma notificação persistente na barra de status garante controle total para encerrar o serviço quando desejar.
          </p>
        </div>

        {/* Seção 4: Direitos LGPD */}
        <div className="p-6 rounded-xl border border-zinc-800 bg-zinc-950/70 space-y-3">
          <h2 className="text-base font-mono uppercase tracking-wider text-zinc-300 font-semibold">
            4. Seus Direitos como Titular de Dados (Artigo 18 da LGPD)
          </h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs">
            <div className="p-3 rounded border border-zinc-800 bg-zinc-900/60">
              <div className="font-semibold text-zinc-200 mb-1">Acesso e Portabilidade</div>
              <div className="text-zinc-400">Você pode exportar todos os seus dados e relatórios a qualquer momento em arquivo universal CSV.</div>
            </div>
            <div className="p-3 rounded border border-zinc-800 bg-zinc-900/60">
              <div className="font-semibold text-zinc-200 mb-1">Eliminação Completa</div>
              <div className="text-zinc-400">Ao limpar os dados do app no Android ou desinstalar, 100% das informações são destruídas do aparelho.</div>
            </div>
            <div className="p-3 rounded border border-zinc-800 bg-zinc-900/60">
              <div className="font-semibold text-zinc-200 mb-1">Revogação a Qualquer Hora</div>
              <div className="text-zinc-400">Permissões de acessibilidade e sobreposição podem ser desligadas instantaneamente nas configurações do sistema.</div>
            </div>
            <div className="p-3 rounded border border-zinc-800 bg-zinc-900/60">
              <div className="font-semibold text-zinc-200 mb-1">Transparência Integral</div>
              <div className="text-zinc-400">Sem rastreadores ocultos, sem analytics de publicidade e sem venda de dados.</div>
            </div>
          </div>
        </div>

        {/* Seção 5: Contato */}
        <div className="p-6 rounded-xl border border-zinc-800 bg-zinc-950 space-y-2">
          <h2 className="text-base font-mono uppercase tracking-wider text-zinc-300 font-semibold">
            5. Encarregado de Proteção de Dados (DPO) & Suporte
          </h2>
          <p className="text-sm text-zinc-400">
            Para dúvidas, sugestões ou solicitações relativas à privacidade e proteção de dados:
          </p>
          <div className="font-mono text-xs text-zinc-300 pt-1">
            <strong>E-mail:</strong> privacidade@traffigorotta.app / suporte@traffigorotta.app<br />
            <strong>Responsável:</strong> Gabriel (Trafego Rotta)<br />
            <strong>Localidade:</strong> São Paulo, SP — Brasil
          </div>
        </div>

        {/* Footer */}
        <div className="text-center pt-8 border-t border-zinc-800 text-xs text-zinc-500 font-mono">
          Trafego Rotta © 2026 • Todos os direitos reservados • Desenvolvido para motoristas profissionais
        </div>
      </div>
    </div>
  );
}
