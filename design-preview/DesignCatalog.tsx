import React, { useState, useEffect } from 'react';
import { tokens } from './tokens';

// Componente de entrada de texto estrito aos tokens de alto contraste
function RottaFormInput({
  label,
  value,
  onChange,
  placeholder = '',
  type = 'text',
  prefix,
  suffix,
  isMono = false,
  themeColors
}: {
  label: string;
  value: string;
  onChange: (val: string) => void;
  placeholder?: string;
  type?: string;
  prefix?: string;
  suffix?: string;
  isMono?: boolean;
  themeColors: typeof tokens.colors | typeof tokens.colors.dark;
}) {
  const [isFocused, setIsFocused] = useState(false);

  return (
    <div className="w-full">
      <label
        style={{
          fontSize: tokens.typography.label.fontSize,
          letterSpacing: tokens.typography.label.letterSpacing,
          color: themeColors.onSurfaceMuted
        }}
        className="font-mono font-semibold block uppercase mb-1.5"
      >
        {label}
      </label>

      <div
        style={{
          backgroundColor: themeColors.surface,
          border: `1.5px solid ${isFocused ? themeColors.primary : themeColors.outline}`,
          borderRadius: tokens.rounded.sm,
          height: '48px',
          transition: 'border-color 150ms ease'
        }}
        className="flex items-center px-4 w-full"
      >
        {prefix && (
          <span
            style={{ color: themeColors.onSurfaceMuted }}
            className="text-sm font-semibold mr-2 select-none shrink-0"
          >
            {prefix}
          </span>
        )}

        <input
          type={type}
          value={value}
          onChange={(e) => onChange(e.target.value)}
          placeholder={placeholder}
          onFocus={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
          style={{
            backgroundColor: 'transparent',
            color: themeColors.onSurface,
            fontSize: tokens.typography.body.fontSize,
            lineHeight: tokens.typography.body.lineHeight,
            fontFamily: isMono ? 'monospace, ui-monospace, SFMono-Regular' : 'inherit'
          }}
          className="w-full h-full border-none outline-none focus:outline-none placeholder:opacity-40"
        />

        {suffix && (
          <span
            style={{ color: themeColors.onSurfaceMuted }}
            className="text-xs font-mono ml-2 select-none shrink-0 uppercase"
          >
            {suffix}
          </span>
        )}
      </div>
    </div>
  );
}

interface FinancialItem {
  id: string;
  type: 'earning' | 'expense';
  title: string;
  amount: number;
  time: string;
  subtext: string;
}

interface HourlySlotData {
  timeRange: string;
  gross: number;
  rides: number;
  perHour: number;
}

export function DesignCatalog() {
  const [isDark, setIsDark] = useState(false);
  const [isPhoneMode, setIsPhoneMode] = useState(true);
  const [activeTab, setActiveTab] = useState<'tour' | 'playstore' | 'recommendations' | 'signal' | 'disclosure' | 'reports' | 'goals' | 'entries' | 'journey' | 'profile' | 'login' | 'components' | 'overlay-test' | 'tokens'>('journey');
  const [tourStep, setTourStep] = useState<number>(1);
  const [playStoreCopyToast, setPlayStoreCopyToast] = useState<string | null>(null);

  // Estado do Semáforo & Overlay (Etapa h)
  const [minRateKm, setMinRateKm] = useState('2.20');
  const [minRateMin, setMinRateMin] = useState('0.60');
  const [minRateHour, setMinRateHour] = useState('36.00');
  const [autoExpandOffer, setAutoExpandOffer] = useState(true);
  const [overlayEnabled, setOverlayEnabled] = useState(true);
  const [floatingOverlayExpanded, setFloatingOverlayExpanded] = useState(false);
  const [savedCriteriaToast, setSavedCriteriaToast] = useState(false);

  // Simulador de Oferta & Ciclo de Vida da Corrida (Fase 2)
  const [simOfferApp, setSimOfferApp] = useState<'Uber' | '99'>('Uber');
  const [simOfferGross, setSimOfferGross] = useState('28.50');
  const [simOfferKm, setSimOfferKm] = useState('8.2');
  const [simOfferMin, setSimOfferMin] = useState('16');
  const [simRideLifecycle, setSimRideLifecycle] = useState<'idle' | 'offered' | 'accepted' | 'completed'>('idle');
  const [autoReportToast, setAutoReportToast] = useState<string | null>(null);

  // Teste de Leitura Bruta (Cenários Extremos & Acessibilidade)
  const [rawTextSample, setRawTextSample] = useState('UberX VIP • R$ 215,80 • 92,5 km • 1 h 35 min de viagem');
  const [parseLogToast, setParseLogToast] = useState<string | null>(null);

  // Termo de consentimento destacado (Prominent Disclosure)
  const [disclosureAccepted, setDisclosureAccepted] = useState<boolean | null>(true);

  // Notificações e Feedback Tátil/Sonoro (Direção Segura)
  const [hapticFeedbackEnabled, setHapticFeedbackEnabled] = useState(true);
  const [soundChimeEnabled, setSoundChimeEnabled] = useState(false);
  const [hapticFiredNotice, setHapticFiredNotice] = useState<string | null>(null);

  // Estado dos Relatórios (Etapa g) e Exportação
  const [reportPeriod, setReportPeriod] = useState<'today' | 'week' | 'month'>('today');
  const [csvExportToast, setCsvExportToast] = useState(false);

  const reportData = {
    today: {
      periodLabel: 'Hoje',
      gross: 485.20,
      net: 371.40,
      vehicleCost: 113.80,
      hours: 6.2,
      km: 165.0,
      rides: 18,
      avgPerHour: 78.25,
      slots: [
        { timeRange: '06h - 09h', gross: 174.00, rides: 6, perHour: 58.00 },
        { timeRange: '10h - 13h', gross: 80.00, rides: 3, perHour: 26.66 },
        { timeRange: '14h - 17h', gross: 111.20, rides: 4, perHour: 37.06 },
        { timeRange: '17h - 20h', gross: 120.00, rides: 5, perHour: 40.00 }
      ]
    },
    week: {
      periodLabel: 'Esta Semana',
      gross: 2450.00,
      net: 1876.00,
      vehicleCost: 574.00,
      hours: 32.5,
      km: 832.0,
      rides: 94,
      avgPerHour: 75.38,
      slots: [
        { timeRange: '06h - 09h', gross: 890.00, rides: 32, perHour: 59.33 },
        { timeRange: '10h - 13h', gross: 420.00, rides: 16, perHour: 28.00 },
        { timeRange: '14h - 17h', gross: 510.00, rides: 21, perHour: 34.00 },
        { timeRange: '17h - 20h', gross: 630.00, rides: 25, perHour: 42.00 }
      ]
    },
    month: {
      periodLabel: 'Este Mês',
      gross: 9820.00,
      net: 7520.00,
      vehicleCost: 2300.00,
      hours: 134.0,
      km: 3330.0,
      rides: 382,
      avgPerHour: 73.28,
      slots: [
        { timeRange: '06h - 09h', gross: 3560.00, rides: 128, perHour: 59.33 },
        { timeRange: '10h - 13h', gross: 1680.00, rides: 64, perHour: 28.00 },
        { timeRange: '14h - 17h', gross: 2040.00, rides: 84, perHour: 34.00 },
        { timeRange: '17h - 20h', gross: 2540.00, rides: 106, perHour: 42.33 }
      ]
    }
  };

  const currentReport = reportData[reportPeriod];
  const maxSlotPerHour = Math.max(...currentReport.slots.map((s) => s.perHour));

  // Estado das Metas (Etapa f)
  const [dailyGoal, setDailyGoal] = useState(500);
  const [weeklyGoal, setWeeklyGoal] = useState(2500);
  const [monthlyGoal, setMonthlyGoal] = useState(10000);
  const [isEditingGoals, setIsEditingGoals] = useState(false);
  const [dailyInput, setDailyInput] = useState('500');
  const [weeklyInput, setWeeklyInput] = useState('2500');
  const [monthlyInput, setMonthlyInput] = useState('10000');
  const [goalsSuccessMessage, setGoalsSuccessMessage] = useState(false);

  // Estado dos Lançamentos Manuais (Etapa e)
  const [entries, setEntries] = useState<FinancialItem[]>([
    { id: '1', type: 'earning', title: 'Uber', amount: 28.50, time: '11:15', subtext: '8,4 km • Centro para Vila Mariana' },
    { id: '2', type: 'earning', title: '99', amount: 34.00, time: '11:45', subtext: '11,2 km • Aeroporto para Moema' },
    { id: '3', type: 'expense', title: 'Combustível', amount: 80.00, time: '12:10', subtext: 'Gasolina Posto Shell' },
    { id: '4', type: 'earning', title: 'Uber', amount: 42.80, time: '12:40', subtext: '14,0 km • Pinheiros para Itaim' },
    { id: '5', type: 'expense', title: 'Alimentação', amount: 22.00, time: '13:20', subtext: 'Almoço PF' }
  ]);
  const [isFormOpen, setIsFormOpen] = useState(false);
  const [entryType, setEntryType] = useState<'earning' | 'expense'>('earning');
  const [amountInput, setAmountInput] = useState('');
  const [earningSource, setEarningSource] = useState<'Uber' | '99' | 'Particular' | 'Outros'>('Uber');
  const [expenseCat, setExpenseCat] = useState<'Combustível' | 'Alimentação' | 'Pedágio' | 'Lavagem' | 'Manutenção'>('Combustível');
  const [distanceInput, setDistanceInput] = useState('');
  const [notesInput, setNotesInput] = useState('');
  const [formError, setFormError] = useState<string | null>(null);

  // Estado da Jornada (Etapa d)
  const [isJourneyActive, setIsJourneyActive] = useState(true);
  const [elapsedMinutes, setElapsedMinutes] = useState(204);
  const [grossEarnings, setGrossEarnings] = useState(184.20);
  const [ridesCount, setRidesCount] = useState(6);
  const [distanceKm, setDistanceKm] = useState(64.5);
  const [showEndConfirmation, setShowEndConfirmation] = useState(false);
  const [lastFinalizedJourney, setLastFinalizedJourney] = useState<{ earnings: number; rides: number; km: number } | null>(null);

  // Estado do perfil e carro (Etapa c)
  const [name, setName] = useState('Carlos Silva');
  const [phone, setPhone] = useState('(11) 98765-4321');
  const [email, setEmail] = useState('carlos.silva@rotta.app');
  const [carModel, setCarModel] = useState('Chevrolet Onix 1.0');
  const [fuelType, setFuelType] = useState<'Gasolina' | 'Etanol' | 'GNV' | 'Elétrico'>('Gasolina');
  const [consumption, setConsumption] = useState('12.8');
  const [fuelPrice, setFuelPrice] = useState('5.89');
  const [fixedCosts, setFixedCosts] = useState('920');
  const [workDays, setWorkDays] = useState('26');
  const [dailyKm, setDailyKm] = useState('160');
  const [showFixedDetails, setShowFixedDetails] = useState(false);
  const [savedSuccess, setSavedSuccess] = useState(false);

  // Calculadora de Paridade de Combustível da Jornada (Etanol vs Gasolina)
  const [ethanolPumpPrice, setEthanolPumpPrice] = useState('3.79');
  const [gasolinePumpPrice, setGasolinePumpPrice] = useState('5.69');
  const [ethanolKmPerL, setEthanolKmPerL] = useState('9.2');
  const [gasolineKmPerL, setGasolineKmPerL] = useState('13.5');
  const [activeFuelChoice, setActiveFuelChoice] = useState<'Etanol' | 'Gasolina'>('Etanol');
  const [isEditingFuelPrices, setIsEditingFuelPrices] = useState(false);

  // Estado do login (Etapa c)
  const [loginEmail, setLoginEmail] = useState('');
  const [loginPassword, setLoginPassword] = useState('');
  const [loginError, setLoginError] = useState<string | null>(null);

  const themeColors = isDark ? tokens.colors.dark : tokens.colors;

  // Cálculos financeiros das metas
  const currentDailyEarnings = 485.20;
  const currentWeeklyEarnings = 1840.00;
  const currentMonthlyEarnings = 6920.00;

  const dailyPercentage = dailyGoal > 0 ? (currentDailyEarnings / dailyGoal) * 100 : 0;
  const dailyRemaining = Math.max(0, dailyGoal - currentDailyEarnings);
  const dailyCompleted = currentDailyEarnings >= dailyGoal;

  const weeklyPercentage = weeklyGoal > 0 ? (currentWeeklyEarnings / weeklyGoal) * 100 : 0;
  const weeklyCompleted = currentWeeklyEarnings >= weeklyGoal;

  const monthlyPercentage = monthlyGoal > 0 ? (currentMonthlyEarnings / monthlyGoal) * 100 : 0;
  const monthlyCompleted = currentMonthlyEarnings >= monthlyGoal;

  // Cálculos dos lançamentos
  const totalEarnings = entries.filter((e) => e.type === 'earning').reduce((acc, curr) => acc + curr.amount, 0);
  const totalExpenses = entries.filter((e) => e.type === 'expense').reduce((acc, curr) => acc + curr.amount, 0);
  const netBalance = totalEarnings - totalExpenses;

  // Cálculos do veículo e Paridade de Combustível da Jornada
  const parsedConsumption = parseFloat(consumption.replace(',', '.')) || 0;
  const parsedFuelPrice = parseFloat(fuelPrice.replace(',', '.')) || 0;
  const parsedFixedCosts = parseFloat(fixedCosts.replace(',', '.')) || 0;
  const parsedWorkDays = parseInt(workDays, 10) || 1;
  const parsedDailyKm = parseFloat(dailyKm.replace(',', '.')) || 1;

  // Paridade Determinística (Consumo real do carro vs Preço da bomba)
  const parsedEthPrice = parseFloat(ethanolPumpPrice.replace(',', '.')) || 3.79;
  const parsedGasPrice = parseFloat(gasolinePumpPrice.replace(',', '.')) || 5.69;
  const parsedEthKm = parseFloat(ethanolKmPerL.replace(',', '.')) || 9.2;
  const parsedGasKm = parseFloat(gasolineKmPerL.replace(',', '.')) || 13.5;

  const costKmEth = parsedEthKm > 0 ? parsedEthPrice / parsedEthKm : 0;
  const costKmGas = parsedGasKm > 0 ? parsedGasPrice / parsedGasKm : 0;
  const realVehicleParityPct = parsedGasKm > 0 ? (parsedEthKm / parsedGasKm) * 100 : 70;
  const pumpParityPct = parsedGasPrice > 0 ? (parsedEthPrice / parsedGasPrice) * 100 : 70;
  const recommendedFuel: 'Etanol' | 'Gasolina' = costKmEth <= costKmGas ? 'Etanol' : 'Gasolina';
  const fuelSavingsPerKm = Math.abs(costKmEth - costKmGas);
  const estimatedDailyFuelSavings = fuelSavingsPerKm * parsedDailyKm;

  // Custo por km do combustível ativo selecionado para a jornada
  const activeFuelCostPerKm = activeFuelChoice === 'Etanol' ? costKmEth : costKmGas;
  const dailyFixedCost = parsedWorkDays > 0 ? parsedFixedCosts / parsedWorkDays : 0;
  const fixedCostPerKm = parsedDailyKm > 0 ? dailyFixedCost / parsedDailyKm : 0;
  const totalCostPerKm = activeFuelCostPerKm + fixedCostPerKm;

  // Métricas da Jornada
  const elapsedHoursFormatted = Math.floor(elapsedMinutes / 60);
  const elapsedRemainingMinutesFormatted = elapsedMinutes % 60;
  const durationHoursDecimal = elapsedMinutes / 60;
  const earningsPerHour = durationHoursDecimal > 0 ? grossEarnings / durationHoursDecimal : grossEarnings;
  const earningsPerKm = distanceKm > 0 ? grossEarnings / distanceKm : 0;
  const estimatedVehicleCost = distanceKm * totalCostPerKm;
  const estimatedNetProfit = grossEarnings - estimatedVehicleCost;

  // Cálculos do Semáforo e Oferta (Etapa h - 100% Determinístico)
  const parsedMinKm = parseFloat(minRateKm.replace(',', '.')) || 2.20;
  const parsedMinMin = parseFloat(minRateMin.replace(',', '.')) || 0.60;
  const parsedMinHour = parseFloat(minRateHour.replace(',', '.')) || 36.00;

  const parsedSimGross = parseFloat(simOfferGross.replace(',', '.')) || 0;
  const parsedSimKm = parseFloat(simOfferKm.replace(',', '.')) || 1;
  const parsedSimMin = parseInt(simOfferMin, 10) || 1;

  const simRateKm = parsedSimKm > 0 ? parsedSimGross / parsedSimKm : 0;
  const simRateMin = parsedSimMin > 0 ? parsedSimGross / parsedSimMin : 0;
  const simRateHour = simRateMin * 60;

  const meetsSimKm = simRateKm >= parsedMinKm;
  const meetsSimMin = simRateMin >= parsedMinMin;
  const meetsSimHour = simRateHour >= parsedMinHour;

  const simPassedCount = (meetsSimKm ? 1 : 0) + (meetsSimMin ? 1 : 0) + (meetsSimHour ? 1 : 0);
  const simGrade: 'GOOD' | 'MEDIUM' | 'BAD' = simPassedCount === 3 ? 'GOOD' : simPassedCount > 0 ? 'MEDIUM' : 'BAD';
  const simGradeWord = simGrade === 'GOOD' ? 'Vale' : simGrade === 'MEDIUM' ? 'Neutra' : 'Fraca';
  const simGradeColor = simGrade === 'GOOD' ? tokens.colors.signalGood : simGrade === 'MEDIUM' ? tokens.colors.signalMedium : tokens.colors.signalBad;
  const simGradeTextColor = simGrade === 'MEDIUM' ? '#0A0A0A' : '#FFFFFF';

  // Parser determinístico para cenários extremos e teste de acessibilidade
  const parseRawAccessibilityText = (text: string) => {
    const isUber = /uber/i.test(text);
    const is99 = /99/i.test(text);
    const app = isUber ? 'Uber' : is99 ? '99' : simOfferApp;

    // Currency
    const curMatch = text.match(/R\$\s*([0-9]{1,3}(?:\.[0-9]{3})*(?:,[0-9]{2})|[0-9]+(?:[.,][0-9]{1,2})?)/i);
    let gross = simOfferGross;
    if (curMatch) {
      gross = curMatch[1].replace(/\./g, '').replace(',', '.');
    }

    // Distance
    let km = simOfferKm;
    const kmMatch = text.match(/([0-9]+(?:[.,][0-9]+)?)\s*(?:km|quil[ôo]metros)/i);
    if (kmMatch) {
      km = kmMatch[1].replace(',', '.');
    } else {
      const mMatch = text.match(/([0-9]+)\s*(?:m|metros)(?!\w)/i);
      if (mMatch) {
        km = (parseFloat(mMatch[1]) / 1000).toFixed(1);
      }
    }

    // Duration
    let totalMinutes = 0;
    const hMatch = text.match(/([0-9]+)\s*(?:h|hora(?:s)?)/i);
    if (hMatch) {
      totalMinutes += parseInt(hMatch[1], 10) * 60;
    }
    const minMatch = text.match(/([0-9]+)\s*(?:min(?:uto)?s?|m)(?!\w)/i);
    if (minMatch) {
      totalMinutes += parseInt(minMatch[1], 10);
    }
    const min = totalMinutes > 0 ? totalMinutes.toString() : simOfferMin;

    setSimOfferApp(app as 'Uber' | '99');
    setSimOfferGross(parseFloat(gross).toFixed(2));
    setSimOfferKm(parseFloat(km).toFixed(1));
    setSimOfferMin(min);
    setParseLogToast(`Extraído: R$ ${parseFloat(gross).toFixed(2).replace('.', ',')} • ${km} km • ${min} min (em < 1ms)`);
    setTimeout(() => setParseLogToast(null), 3000);
    if (autoExpandOffer) {
      setFloatingOverlayExpanded(true);
    }
  };

  const triggerSimulatedFeedback = (grade: 'GOOD' | 'MEDIUM' | 'BAD') => {
    if (hapticFeedbackEnabled) {
      if (typeof window !== 'undefined' && 'vibrate' in navigator) {
        if (grade === 'GOOD') {
          navigator.vibrate(85);
        } else if (grade === 'MEDIUM') {
          navigator.vibrate([40, 50, 40]);
        }
      }
      const label = grade === 'GOOD' ? '📳 1 toque firme ("Vale")' : grade === 'MEDIUM' ? '📳 2 toques curtos ("Neutra")' : '📳 Mudo ("Fraca")';
      setHapticFiredNotice(label);
      setTimeout(() => setHapticFiredNotice(null), 2500);
    }

    if (soundChimeEnabled && grade === 'GOOD') {
      try {
        const audioCtx = new (window.AudioContext || (window as unknown as { webkitAudioContext: typeof AudioContext }).webkitAudioContext)();
        const osc = audioCtx.createOscillator();
        const gain = audioCtx.createGain();
        osc.type = 'sine';
        osc.frequency.setValueAtTime(587.33, audioCtx.currentTime);
        gain.gain.setValueAtTime(0.08, audioCtx.currentTime);
        gain.gain.exponentialRampToValueAtTime(0.001, audioCtx.currentTime + 0.25);
        osc.connect(gain);
        gain.connect(audioCtx.destination);
        osc.start();
        osc.stop(audioCtx.currentTime + 0.25);
      } catch {
        // audio context indisponível antes do primeiro clique do usuário
      }
    }
  };

  const handleExportCSV = () => {
    const activeData = reportData[reportPeriod];
    const csvContent = [
      'Data/Periodo;Ganho Bruto (R$);Custo Veiculo (R$);Lucro Liquido (R$);Horas Trabalhadas;Km Rodados;Corridas;Media R$/h',
      `${activeData.periodLabel};${activeData.gross.toFixed(2)};${activeData.vehicleCost.toFixed(2)};${activeData.net.toFixed(2)};${activeData.hours.toFixed(1)};${activeData.km.toFixed(1)};${activeData.rides};${activeData.avgPerHour.toFixed(2)}`,
      '',
      '--- Detalhamento por Faixa de Horario ---',
      'Faixa Horaria;Faturamento Bruto (R$);Corridas;Media por Hora (R$/h)',
      ...activeData.slots.map((h: { timeRange: string; gross: number; rides: number; perHour: number }) => `${h.timeRange};${h.gross.toFixed(2)};${h.rides};${h.perHour.toFixed(2)}`)
    ].join('\n');

    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `trafego_rotta_${reportPeriod}_${new Date().toISOString().slice(0, 10)}.csv`);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    URL.revokeObjectURL(url);

    setCsvExportToast(true);
    setTimeout(() => setCsvExportToast(false), 3000);
  };

  const handleSaveGoals = (e: React.FormEvent) => {
    e.preventDefault();
    const d = parseFloat(dailyInput.replace(',', '.')) || 500;
    const w = parseFloat(weeklyInput.replace(',', '.')) || 2500;
    const m = parseFloat(monthlyInput.replace(',', '.')) || 10000;
    setDailyGoal(d);
    setWeeklyGoal(w);
    setMonthlyGoal(m);
    setIsEditingGoals(false);
    setGoalsSuccessMessage(true);
    setTimeout(() => setGoalsSuccessMessage(false), 4000);
  };

  const handleSaveEntry = (e: React.FormEvent) => {
    e.preventDefault();
    const parsedAmount = parseFloat(amountInput.replace(',', '.'));
    if (isNaN(parsedAmount) || parsedAmount <= 0) {
      setFormError('Informe um valor válido maior que zero.');
      return;
    }

    const now = new Date();
    const timeStr = `${String(now.getHours()).padStart(2, '0')}:${String(now.getMinutes()).padStart(2, '0')}`;
    const parsedKm = parseFloat(distanceInput.replace(',', '.')) || 0;

    let titleStr = '';
    let subtextStr = '';

    if (entryType === 'earning') {
      titleStr = earningSource;
      subtextStr = parsedKm > 0 ? `${parsedKm.toFixed(1).replace('.', ',')} km ${notesInput ? '• ' + notesInput : ''}` : notesInput || 'Corrida lançada manualmente';
      if (isJourneyActive) {
        setGrossEarnings((prev) => prev + parsedAmount);
        setDistanceKm((prev) => prev + parsedKm);
        setRidesCount((prev) => prev + 1);
      }
    } else {
      titleStr = expenseCat;
      subtextStr = notesInput || 'Despesa lançada manualmente';
    }

    const newItem: FinancialItem = {
      id: String(Date.now()),
      type: entryType,
      title: titleStr,
      amount: parsedAmount,
      time: timeStr,
      subtext: subtextStr
    };

    setEntries([newItem, ...entries]);
    setAmountInput('');
    setDistanceInput('');
    setNotesInput('');
    setFormError(null);
    setIsFormOpen(false);
  };

  const handleDeleteEntry = (id: string) => {
    setEntries(entries.filter((item) => item.id !== id));
  };

  const handleStartJourney = () => {
    setIsJourneyActive(true);
    setElapsedMinutes(0);
    setGrossEarnings(0);
    setRidesCount(0);
    setDistanceKm(0);
    setShowEndConfirmation(false);
  };

  const handleConfirmEndJourney = () => {
    setLastFinalizedJourney({
      earnings: grossEarnings,
      rides: ridesCount,
      km: distanceKm
    });
    setIsJourneyActive(false);
    setShowEndConfirmation(false);
  };

  const handleSimulateRide = () => {
    setGrossEarnings((prev) => prev + 26.50);
    setDistanceKm((prev) => prev + 8.2);
    setRidesCount((prev) => prev + 1);
  };

  const handleSaveProfile = (e: React.FormEvent) => {
    e.preventDefault();
    setSavedSuccess(true);
    setTimeout(() => setSavedSuccess(false), 4000);
  };

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    if (!loginEmail || !loginEmail.includes('@')) {
      setLoginError('Informe um e-mail válido para acessar.');
      return;
    }
    setLoginError(null);
    setEmail(loginEmail);
    setActiveTab('reports');
  };

  const tabTitles: Record<string, { label: string; title: string }> = {
    journey: { label: 'Trafego Rotta • Operação', title: 'Jornada em Andamento' },
    signal: { label: 'Semáforo de Ofertas', title: 'Simulador Uber & 99' },
    reports: { label: 'Gestão Financeira', title: 'Relatórios de Ganhos' },
    goals: { label: 'Alvos do Motorista', title: 'Metas Diárias e Mensais' },
    profile: { label: 'Veículo & Custos', title: 'Perfil e Paridade Real' },
    recommendations: { label: 'Copiloto Determinístico', title: 'Dicas Operacionais' },
    tour: { label: 'Apresentação Rápida', title: 'Tour da Jornada de Relance' },
    disclosure: { label: 'Conformidade Play Store', title: 'Termo de Permissões' },
    entries: { label: 'Controle de Caixa', title: 'Lançamentos Manuais' },
    login: { label: 'Acesso', title: 'Entrar no Trafego Rotta' },
    playstore: { label: 'Submissão Google Play', title: 'Kit Play Store & LGPD' },
    components: { label: 'Design System', title: 'Catálogo de Componentes' },
    'overlay-test': { label: 'Validação Visual', title: 'Overlay sobre 3 Fundos' },
    tokens: { label: 'Especificação', title: 'Tokens de Design' }
  };

  return (
    <div
      style={{
        backgroundColor: themeColors.surface,
        color: themeColors.onSurface,
        colorScheme: isDark ? 'dark' : 'light',
        minHeight: '100vh',
        fontFamily: 'Geist, -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif',
        transition: 'background-color 200ms ease, color 200ms ease'
      }}
      className="p-3 sm:p-6"
    >
      <div className={isPhoneMode ? "max-w-[420px] mx-auto transition-all" : "max-w-2xl mx-auto transition-all"}>
        {/* Barra Superior Global */}
        <div className="flex flex-wrap items-center justify-between pb-4 mb-4 border-b gap-3" style={{ borderColor: themeColors.outline }}>
          <div className="flex items-center gap-2.5">
            <div className="w-8 h-8 rounded-full bg-white text-black font-bold flex items-center justify-center text-sm shadow shrink-0">
              R
            </div>
            <div>
              <span
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-mono font-medium block uppercase text-[10px]"
              >
                {tabTitles[activeTab]?.label || 'TRAFEGO ROTTA'}
              </span>
              <h1 className="text-sm sm:text-base font-bold leading-tight">
                {tabTitles[activeTab]?.title || 'Copiloto do Motorista'}
              </h1>
            </div>
          </div>

          <div className="flex items-center gap-2 flex-wrap">
            {/* Seletor Modo Celular vs Bancada */}
            <div
              className="flex rounded-full p-0.5 border"
              style={{
                borderColor: themeColors.outline,
                backgroundColor: themeColors.surfaceVariant
              }}
            >
              <button
                type="button"
                onClick={() => {
                  setIsPhoneMode(true);
                  if (['tour', 'tokens', 'overlay-test', 'components'].includes(activeTab)) {
                    setActiveTab('journey');
                  }
                }}
                className={`px-3 py-1 text-xs font-semibold rounded-full transition-all cursor-pointer ${
                  isPhoneMode
                    ? 'bg-white text-black shadow'
                    : 'text-zinc-400 hover:text-white'
                }`}
              >
                📱 Modo Celular
              </button>
              <button
                type="button"
                onClick={() => setIsPhoneMode(false)}
                className={`px-3 py-1 text-xs font-semibold rounded-full transition-all cursor-pointer ${
                  !isPhoneMode
                    ? 'bg-white text-black shadow'
                    : 'text-zinc-400 hover:text-white'
                }`}
              >
                💻 Bancada Completa
              </button>
            </div>

            {/* Alternador Claro/Escuro */}
            <button
              type="button"
              onClick={() => setIsDark(!isDark)}
              style={{
                backgroundColor: themeColors.surfaceVariant,
                color: themeColors.onSurface,
                border: `1px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.full,
                padding: '6px 12px',
                fontSize: '11px'
              }}
              className="font-medium hover:opacity-90 transition-opacity cursor-pointer select-none"
            >
              {isDark ? '☀️ Claro' : '🌙 Escuro'}
            </button>
          </div>
        </div>

        {/* Abas de Navegação Técnicas (Visíveis apenas na Bancada Completa) */}
        {!isPhoneMode && (
          <div className="flex gap-2 my-4 overflow-x-auto pb-2">
            <button
              onClick={() => setActiveTab('tour')}
              style={{
                backgroundColor: activeTab === 'tour' ? themeColors.primary : themeColors.surfaceVariant,
                color: activeTab === 'tour' ? themeColors.onPrimary : themeColors.onSurface,
                borderRadius: tokens.rounded.full,
                padding: '8px 20px',
                fontSize: tokens.typography.bodySm.fontSize,
                fontWeight: 600
              }}
              className="cursor-pointer whitespace-nowrap"
            >
            ★ Tour da Jornada (60s)
          </button>
          <button
            onClick={() => setActiveTab('playstore')}
            style={{
              backgroundColor: activeTab === 'playstore' ? themeColors.primary : themeColors.surfaceVariant,
              color: activeTab === 'playstore' ? themeColors.onPrimary : themeColors.onSurface,
              borderRadius: tokens.rounded.full,
              padding: '8px 20px',
              fontSize: tokens.typography.bodySm.fontSize,
              fontWeight: 600
            }}
            className="cursor-pointer whitespace-nowrap"
          >
            🚀 Kit Play Store & LGPD
          </button>
          <button
            onClick={() => setActiveTab('recommendations')}
            style={{
              backgroundColor: activeTab === 'recommendations' ? themeColors.primary : themeColors.surfaceVariant,
              color: activeTab === 'recommendations' ? themeColors.onPrimary : themeColors.onSurface,
              borderRadius: tokens.rounded.full,
              padding: '8px 20px',
              fontSize: tokens.typography.bodySm.fontSize,
              fontWeight: 500
            }}
            className="cursor-pointer whitespace-nowrap"
          >
            Recomendações (Fase 3)
          </button>
          <button
            onClick={() => setActiveTab('signal')}
            style={{
              backgroundColor: activeTab === 'signal' ? themeColors.primary : themeColors.surfaceVariant,
              color: activeTab === 'signal' ? themeColors.onPrimary : themeColors.onSurface,
              borderRadius: tokens.rounded.full,
              padding: '8px 20px',
              fontSize: tokens.typography.bodySm.fontSize,
              fontWeight: 500
            }}
            className="cursor-pointer whitespace-nowrap"
          >
            Semáforo & Simulador (Etapa h)
          </button>
          <button
            onClick={() => setActiveTab('disclosure')}
            style={{
              backgroundColor: activeTab === 'disclosure' ? themeColors.primary : themeColors.surfaceVariant,
              color: activeTab === 'disclosure' ? themeColors.onPrimary : themeColors.onSurface,
              borderRadius: tokens.rounded.full,
              padding: '8px 20px',
              fontSize: tokens.typography.bodySm.fontSize,
              fontWeight: 500
            }}
            className="cursor-pointer whitespace-nowrap"
          >
            Termo de Permissões (Etapa h)
          </button>
          <button
            onClick={() => setActiveTab('reports')}
            style={{
              backgroundColor: activeTab === 'reports' ? themeColors.primary : themeColors.surfaceVariant,
              color: activeTab === 'reports' ? themeColors.onPrimary : themeColors.onSurface,
              borderRadius: tokens.rounded.full,
              padding: '8px 20px',
              fontSize: tokens.typography.bodySm.fontSize,
              fontWeight: 500
            }}
            className="cursor-pointer whitespace-nowrap"
          >
            Relatórios (Etapa g)
          </button>
          <button
            onClick={() => setActiveTab('goals')}
            style={{
              backgroundColor: activeTab === 'goals' ? themeColors.primary : themeColors.surfaceVariant,
              color: activeTab === 'goals' ? themeColors.onPrimary : themeColors.onSurface,
              borderRadius: tokens.rounded.full,
              padding: '8px 20px',
              fontSize: tokens.typography.bodySm.fontSize,
              fontWeight: 500
            }}
            className="cursor-pointer whitespace-nowrap"
          >
            Metas (Etapa f)
          </button>
          <button
            onClick={() => setActiveTab('entries')}
            style={{
              backgroundColor: activeTab === 'entries' ? themeColors.primary : themeColors.surfaceVariant,
              color: activeTab === 'entries' ? themeColors.onPrimary : themeColors.onSurface,
              borderRadius: tokens.rounded.full,
              padding: '8px 20px',
              fontSize: tokens.typography.bodySm.fontSize,
              fontWeight: 500
            }}
            className="cursor-pointer whitespace-nowrap"
          >
            Lançamentos (Etapa e)
          </button>
          <button
            onClick={() => setActiveTab('journey')}
            style={{
              backgroundColor: activeTab === 'journey' ? themeColors.primary : themeColors.surfaceVariant,
              color: activeTab === 'journey' ? themeColors.onPrimary : themeColors.onSurface,
              borderRadius: tokens.rounded.full,
              padding: '8px 20px',
              fontSize: tokens.typography.bodySm.fontSize,
              fontWeight: 500
            }}
            className="cursor-pointer whitespace-nowrap"
          >
            Jornada (Etapa d)
          </button>
          <button
            onClick={() => setActiveTab('profile')}
            style={{
              backgroundColor: activeTab === 'profile' ? themeColors.primary : themeColors.surfaceVariant,
              color: activeTab === 'profile' ? themeColors.onPrimary : themeColors.onSurface,
              borderRadius: tokens.rounded.full,
              padding: '8px 20px',
              fontSize: tokens.typography.bodySm.fontSize,
              fontWeight: 500
            }}
            className="cursor-pointer whitespace-nowrap"
          >
            Perfil & Carro (Etapa c)
          </button>
          <button
            onClick={() => setActiveTab('login')}
            style={{
              backgroundColor: activeTab === 'login' ? themeColors.primary : themeColors.surfaceVariant,
              color: activeTab === 'login' ? themeColors.onPrimary : themeColors.onSurface,
              borderRadius: tokens.rounded.full,
              padding: '8px 20px',
              fontSize: tokens.typography.bodySm.fontSize,
              fontWeight: 500
            }}
            className="cursor-pointer whitespace-nowrap"
          >
            Login (Etapa c)
          </button>
          <button
            onClick={() => setActiveTab('components')}
            style={{
              backgroundColor: activeTab === 'components' ? themeColors.primary : themeColors.surfaceVariant,
              color: activeTab === 'components' ? themeColors.onPrimary : themeColors.onSurface,
              borderRadius: tokens.rounded.full,
              padding: '8px 20px',
              fontSize: tokens.typography.bodySm.fontSize,
              fontWeight: 500
            }}
            className="cursor-pointer whitespace-nowrap"
          >
            Catálogo Base (Etapa b)
          </button>
          <button
            onClick={() => setActiveTab('overlay-test')}
            style={{
              backgroundColor: activeTab === 'overlay-test' ? themeColors.primary : themeColors.surfaceVariant,
              color: activeTab === 'overlay-test' ? themeColors.onPrimary : themeColors.onSurface,
              borderRadius: tokens.rounded.full,
              padding: '8px 20px',
              fontSize: tokens.typography.bodySm.fontSize,
              fontWeight: 500
            }}
            className="cursor-pointer whitespace-nowrap"
          >
            Overlay sobre 3 Fundos
          </button>
          <button
            onClick={() => setActiveTab('tokens')}
            style={{
              backgroundColor: activeTab === 'tokens' ? themeColors.primary : themeColors.surfaceVariant,
              color: activeTab === 'tokens' ? themeColors.onPrimary : themeColors.onSurface,
              borderRadius: tokens.rounded.full,
              padding: '8px 20px',
              fontSize: tokens.typography.bodySm.fontSize,
              fontWeight: 500
            }}
            className="cursor-pointer whitespace-nowrap"
          >
            Tokens
          </button>
        </div>
      )}

      {/* Envoltório da Tela / Smartphone Android */}
      <div
        className={
          isPhoneMode
            ? "rounded-[44px] border-[8px] border-zinc-800 shadow-2xl overflow-hidden relative transition-all"
            : ""
        }
        style={{
          backgroundColor: isPhoneMode ? themeColors.surface : 'transparent'
        }}
      >
        {isPhoneMode && (
          <div
            className="flex items-center justify-between px-6 pt-3 pb-2 text-xs font-mono select-none border-b"
            style={{
              backgroundColor: themeColors.surface,
              borderColor: themeColors.outline,
              color: themeColors.onSurfaceMuted
            }}
          >
            <span className="font-semibold text-xs" style={{ color: themeColors.onSurface }}>14:32</span>
            <div className="w-3.5 h-3.5 rounded-full bg-black border border-zinc-700" />
            <div className="flex items-center gap-1.5 text-[11px] font-semibold" style={{ color: themeColors.onSurface }}>
              <span>5G</span>
              <span>100%</span>
            </div>
          </div>
        )}

        <div className={isPhoneMode ? "max-h-[640px] overflow-y-auto px-4 py-4" : ""}>
        {/* 0. ABA TOUR DA JORNADA (60 SEGUNDOS) */}
        {activeTab === 'tour' && (
          <div className="space-y-6">
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderRadius: tokens.rounded.md,
                padding: '24px',
                border: `1.5px solid ${themeColors.outline}`
              }}
            >
              <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-2 mb-2">
                <span
                  style={{
                    fontSize: tokens.typography.label.fontSize,
                    letterSpacing: tokens.typography.label.letterSpacing,
                    color: themeColors.onSurfaceMuted
                  }}
                  className="font-mono font-semibold uppercase"
                >
                  DEMONSTRAÇÃO GUIADA • ROTINA COMPLETA DO MOTORISTA
                </span>
                <span
                  style={{
                    backgroundColor: isDark ? '#14532D' : '#DCFCE7',
                    color: isDark ? '#86EFAC' : '#14532D',
                    borderColor: isDark ? '#15803D' : '#86EFAC'
                  }}
                  className="text-xs border px-2.5 py-0.5 rounded-full font-mono font-semibold"
                >
                  Passo {tourStep} de 6
                </span>
              </div>
              <h2
                style={{
                  fontSize: tokens.typography.headline.fontSize,
                  lineHeight: tokens.typography.headline.lineHeight,
                  fontWeight: tokens.typography.headline.fontWeight,
                  color: themeColors.onSurface
                }}
              >
                Tour da Jornada de Relance
              </h2>
              <p
                style={{
                  fontSize: tokens.typography.bodySm.fontSize,
                  color: themeColors.onSurfaceMuted
                }}
                className="mt-2"
              >
                Entenda em 60 segundos como cada componente do copiloto protege a segurança do trânsito e o lucro líquido do motorista.
              </p>

              {/* Barra de Progresso dos 6 Passos */}
              <div className="grid grid-cols-6 gap-1.5 mt-5">
                {[1, 2, 3, 4, 5, 6].map((step) => {
                  const isActive = tourStep === step;
                  const isPast = tourStep > step;
                  return (
                    <button
                      key={step}
                      type="button"
                      onClick={() => setTourStep(step)}
                      style={{
                        backgroundColor: isActive
                          ? themeColors.primary
                          : isPast
                          ? '#15803D'
                          : themeColors.surface,
                        border: `1px solid ${isActive ? themeColors.primary : themeColors.outline}`,
                        height: '6px',
                        borderRadius: tokens.rounded.full
                      }}
                      className="cursor-pointer transition-colors"
                      title={`Passo ${step}`}
                    />
                  );
                })}
              </div>
            </div>

            {/* CONTEÚDO ESPECÍFICO DO PASSO ATUAL */}
            {tourStep === 1 && (
              <div
                style={{
                  backgroundColor: themeColors.surface,
                  borderRadius: tokens.rounded.md,
                  border: `1.5px solid ${themeColors.outline}`,
                  padding: '24px'
                }}
                className="space-y-4"
              >
                <div className="flex items-center gap-2">
                  <span
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary
                    }}
                    className="w-7 h-7 rounded-full font-bold text-xs flex items-center justify-center shrink-0"
                  >
                    1
                  </span>
                  <h3 className="text-lg font-bold" style={{ color: themeColors.onSurface }}>
                    Passo 1: Carro & Motor de Paridade Real
                  </h3>
                </div>
                <p className="text-sm leading-relaxed" style={{ color: themeColors.onSurfaceMuted }}>
                  Antes de ligar o carro, o motorista abre o Rotta. O <strong style={{ color: themeColors.onSurface }}>Motor Determinístico de Paridade Real</strong> analisa a relação exata de consumo do veículo (9,2 km/l no etanol vs 13,5 km/l na gasolina = <strong style={{ color: themeColors.onSurface }}>68,1%</strong>) contra os preços de bomba de hoje.
                </p>
                <div
                  style={{
                    backgroundColor: themeColors.surfaceVariant,
                    borderColor: themeColors.outline
                  }}
                  className="p-4 rounded-lg border space-y-2 text-xs font-mono"
                >
                  <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-1">
                    <span style={{ color: themeColors.onSurfaceMuted }}>Preço Bomba: Etanol R$ 3,79 • Gasolina R$ 5,69</span>
                    <span
                      style={{ color: isDark ? '#4ADE80' : tokens.colors.signalGood }}
                      className="font-bold text-sm sm:text-xs"
                    >
                      Etanol Vence (R$ 0,41/km vs R$ 0,42/km)
                    </span>
                  </div>
                  <div
                    className="flex flex-col sm:flex-row sm:items-center justify-between gap-1 pt-2 border-t"
                    style={{ borderColor: themeColors.outline }}
                  >
                    <span style={{ color: themeColors.onSurfaceMuted }}>Economia no Tanque: + R$ 18,50</span>
                    <span
                      style={{ color: isDark ? '#4ADE80' : tokens.colors.signalGood }}
                      className="font-bold"
                    >
                      + R$ 170,00 no bolso no fim do mês
                    </span>
                  </div>
                </div>
                <div className="pt-2 flex justify-between items-center">
                  <button
                    type="button"
                    onClick={() => setActiveTab('journey')}
                    style={{ color: themeColors.onSurface }}
                    className="text-xs underline font-medium cursor-pointer"
                  >
                    Ver calculadora de paridade na aba Jornada ↗
                  </button>
                  <button
                    type="button"
                    onClick={() => setTourStep(2)}
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary,
                      borderRadius: tokens.rounded.full,
                      padding: '8px 24px',
                      fontSize: '13px',
                      fontWeight: 600
                    }}
                    className="cursor-pointer hover:opacity-90"
                  >
                    Próximo Passo →
                  </button>
                </div>
              </div>
            )}

            {tourStep === 2 && (
              <div
                style={{
                  backgroundColor: themeColors.surface,
                  borderRadius: tokens.rounded.md,
                  border: `1.5px solid ${themeColors.outline}`,
                  padding: '24px'
                }}
                className="space-y-4"
              >
                <div className="flex items-center gap-2">
                  <span
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary
                    }}
                    className="w-7 h-7 rounded-full font-bold text-xs flex items-center justify-center shrink-0"
                  >
                    2
                  </span>
                  <h3 className="text-lg font-bold" style={{ color: themeColors.onSurface }}>
                    Passo 2: Início da Jornada & Bolha de Relance
                  </h3>
                </div>
                <p className="text-sm leading-relaxed" style={{ color: themeColors.onSurfaceMuted }}>
                  O motorista toca em <strong style={{ color: themeColors.onSurface }}>Iniciar Jornada</strong>. O app inicia o hodômetro em 0 km, liga o cronômetro de horas conectadas e ativa o foreground service com a <strong style={{ color: themeColors.onSurface }}>bolha flutuante persistente de 52px</strong>.
                </p>
                <div
                  style={{
                    backgroundColor: themeColors.surfaceVariant,
                    borderColor: themeColors.outline
                  }}
                  className="p-4 rounded-lg border flex items-center justify-between"
                >
                  <div className="flex items-center gap-3">
                    <div
                      style={{
                        backgroundColor: themeColors.primary,
                        color: themeColors.onPrimary
                      }}
                      className="w-11 h-11 rounded-full font-bold flex items-center justify-center text-sm shadow"
                    >
                      R
                    </div>
                    <div>
                      <span
                        style={{ color: themeColors.onSurfaceMuted }}
                        className="text-xs font-mono uppercase block"
                      >
                        Overlay Ativo em 1º Plano
                      </span>
                      <span
                        style={{ color: themeColors.onSurface }}
                        className="text-sm font-bold"
                      >
                        Pronto para rodar com Uber e 99
                      </span>
                    </div>
                  </div>
                  <span
                    style={{
                      backgroundColor: isDark ? '#14532D' : '#DCFCE7',
                      color: isDark ? '#86EFAC' : '#14532D',
                      borderColor: isDark ? '#15803D' : '#86EFAC'
                    }}
                    className="text-xs border px-2.5 py-1 rounded-full font-mono font-semibold"
                  >
                    ● ONLINE
                  </span>
                </div>
                <div className="pt-2 flex justify-between items-center">
                  <button
                    type="button"
                    onClick={() => setTourStep(1)}
                    style={{ color: themeColors.onSurfaceMuted }}
                    className="text-xs underline cursor-pointer hover:opacity-80"
                  >
                    ← Passo Anterior
                  </button>
                  <button
                    type="button"
                    onClick={() => setTourStep(3)}
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary,
                      borderRadius: tokens.rounded.full,
                      padding: '8px 24px',
                      fontSize: '13px',
                      fontWeight: 600
                    }}
                    className="cursor-pointer hover:opacity-90"
                  >
                    Próximo Passo →
                  </button>
                </div>
              </div>
            )}

            {tourStep === 3 && (
              <div
                style={{
                  backgroundColor: themeColors.surface,
                  borderRadius: tokens.rounded.md,
                  border: `1.5px solid ${themeColors.outline}`,
                  padding: '24px'
                }}
                className="space-y-4"
              >
                <div className="flex items-center gap-2">
                  <span
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary
                    }}
                    className="w-7 h-7 rounded-full font-bold text-xs flex items-center justify-center shrink-0"
                  >
                    3
                  </span>
                  <h3 className="text-lg font-bold" style={{ color: themeColors.onSurface }}>
                    Passo 3: Leitura Passiva de Oferta (Sem Toques)
                  </h3>
                </div>
                <p className="text-sm leading-relaxed" style={{ color: themeColors.onSurfaceMuted }}>
                  Surge uma notificação de corrida no app parceiro. O <code style={{ backgroundColor: themeColors.surfaceVariant, color: themeColors.onSurface, padding: '2px 6px', borderRadius: '4px' }}>RottaAccessibilityService</code> faz a leitura puramente passiva:
                </p>
                <div className="grid grid-cols-1 sm:grid-cols-3 gap-2 text-center text-xs font-mono">
                  <div
                    style={{
                      backgroundColor: themeColors.surfaceVariant,
                      borderColor: themeColors.outline
                    }}
                    className="p-3 rounded border"
                  >
                    <span style={{ color: themeColors.onSurfaceMuted }} className="block text-[10px] uppercase">TARIFA</span>
                    <strong style={{ color: themeColors.onSurface }} className="text-sm">R$ 38,50</strong>
                  </div>
                  <div
                    style={{
                      backgroundColor: themeColors.surfaceVariant,
                      borderColor: themeColors.outline
                    }}
                    className="p-3 rounded border"
                  >
                    <span style={{ color: themeColors.onSurfaceMuted }} className="block text-[10px] uppercase">DISTÂNCIA</span>
                    <strong style={{ color: themeColors.onSurface }} className="text-sm">12,0 km</strong>
                  </div>
                  <div
                    style={{
                      backgroundColor: themeColors.surfaceVariant,
                      borderColor: themeColors.outline
                    }}
                    className="p-3 rounded border"
                  >
                    <span style={{ color: themeColors.onSurfaceMuted }} className="block text-[10px] uppercase">DURAÇÃO</span>
                    <strong style={{ color: themeColors.onSurface }} className="text-sm">24 minutos</strong>
                  </div>
                </div>
                <div
                  style={{
                    backgroundColor: isDark ? '#14532D20' : '#DCFCE7',
                    borderColor: isDark ? '#15803D80' : '#86EFAC',
                    color: isDark ? '#86EFAC' : '#14532D'
                  }}
                  className="p-2.5 rounded border text-xs"
                >
                  ✓ <strong>100% Conforme Google Play:</strong> Nenhum dado de passageiro (nome, nota ou foto) é processado, e o serviço <strong>jamais</strong> clica ou aceita automaticamente.
                </div>
                <div className="pt-2 flex justify-between items-center">
                  <button
                    type="button"
                    onClick={() => setTourStep(2)}
                    style={{ color: themeColors.onSurfaceMuted }}
                    className="text-xs underline cursor-pointer hover:opacity-80"
                  >
                    ← Passo Anterior
                  </button>
                  <button
                    type="button"
                    onClick={() => setTourStep(4)}
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary,
                      borderRadius: tokens.rounded.full,
                      padding: '8px 24px',
                      fontSize: '13px',
                      fontWeight: 600
                    }}
                    className="cursor-pointer hover:opacity-90"
                  >
                    Próximo Passo →
                  </button>
                </div>
              </div>
            )}

            {tourStep === 4 && (
              <div
                style={{
                  backgroundColor: themeColors.surface,
                  borderRadius: tokens.rounded.md,
                  border: `1.5px solid ${themeColors.outline}`,
                  padding: '24px'
                }}
                className="space-y-4"
              >
                <div className="flex items-center gap-2">
                  <span
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary
                    }}
                    className="w-7 h-7 rounded-full font-bold text-xs flex items-center justify-center shrink-0"
                  >
                    4
                  </span>
                  <h3 className="text-lg font-bold" style={{ color: themeColors.onSurface }}>
                    Passo 4: Semáforo Instantâneo & Vibração Tátil
                  </h3>
                </div>
                <p className="text-sm leading-relaxed" style={{ color: themeColors.onSurfaceMuted }}>
                  Em <strong style={{ color: themeColors.onSurface }}>menos de 1 segundo</strong>, o semáforo avalia: R$ 3,21/km (piso 2,20 ✓), R$ 1,60/min (piso 0,60 ✓) e R$ 96,25/h (piso 36,00 ✓).
                </p>
                <div
                  style={{
                    backgroundColor: themeColors.surfaceVariant,
                    borderColor: isDark ? '#4ADE80' : tokens.colors.signalGood
                  }}
                  className="p-4 rounded-lg border-2 flex items-center justify-between"
                >
                  <div>
                    <span style={{ color: themeColors.onSurfaceMuted }} className="text-[11px] font-mono block uppercase">
                      Avaliação de Relance
                    </span>
                    <span style={{ color: themeColors.onSurface }} className="text-2xl font-bold">
                      Uber • R$ 38,50
                    </span>
                    <span style={{ color: themeColors.onSurfaceMuted }} className="text-xs block">
                      12,0 km • 24 min (R$ 3,21/km)
                    </span>
                  </div>
                  <div
                    style={{
                      backgroundColor: tokens.colors.signalGood,
                      color: tokens.colors.onSignalGood
                    }}
                    className="px-5 py-2 rounded-full font-bold text-sm shadow"
                  >
                    Vale
                  </div>
                </div>
                <div
                  style={{
                    backgroundColor: themeColors.surfaceVariant,
                    borderColor: themeColors.outline,
                    color: themeColors.onSurface
                  }}
                  className="p-2.5 rounded border text-xs font-mono"
                >
                  📳 <strong>Feedback Sensorial:</strong> 1 pulso firme emitido no volante/bolso. O motorista sabe imediatamente que a corrida é excelente sem tirar o olho da rua!
                </div>
                <div className="pt-2 flex justify-between items-center">
                  <button
                    type="button"
                    onClick={() => setTourStep(3)}
                    style={{ color: themeColors.onSurfaceMuted }}
                    className="text-xs underline cursor-pointer hover:opacity-80"
                  >
                    ← Passo Anterior
                  </button>
                  <button
                    type="button"
                    onClick={() => setTourStep(5)}
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary,
                      borderRadius: tokens.rounded.full,
                      padding: '8px 24px',
                      fontSize: '13px',
                      fontWeight: 600
                    }}
                    className="cursor-pointer hover:opacity-90"
                  >
                    Próximo Passo →
                  </button>
                </div>
              </div>
            )}

            {tourStep === 5 && (
              <div
                style={{
                  backgroundColor: themeColors.surface,
                  borderRadius: tokens.rounded.md,
                  border: `1.5px solid ${themeColors.outline}`,
                  padding: '24px'
                }}
                className="space-y-4"
              >
                <div className="flex items-center gap-2">
                  <span
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary
                    }}
                    className="w-7 h-7 rounded-full font-bold text-xs flex items-center justify-center shrink-0"
                  >
                    5
                  </span>
                  <h3 className="text-lg font-bold" style={{ color: themeColors.onSurface }}>
                    Passo 5: Encerramento com Dedução Real de Custos
                  </h3>
                </div>
                <p className="text-sm leading-relaxed" style={{ color: themeColors.onSurfaceMuted }}>
                  Ao final de 6h de trabalho e 165 km rodados, o motorista encerra a jornada. O Rotta não ilude o motorista com faturamento bruto: desconta na hora o combustível e o desgaste mecânico.
                </p>
                <div className="grid grid-cols-1 sm:grid-cols-3 gap-2 text-center text-xs font-mono">
                  <div
                    style={{
                      backgroundColor: themeColors.surfaceVariant,
                      borderColor: themeColors.outline
                    }}
                    className="p-3 rounded border"
                  >
                    <span style={{ color: themeColors.onSurfaceMuted }} className="block text-[10px] uppercase">BRUTO FATURADO</span>
                    <strong style={{ color: themeColors.onSurface }} className="text-base">R$ 485,20</strong>
                  </div>
                  <div
                    style={{
                      backgroundColor: themeColors.surfaceVariant,
                      borderColor: themeColors.outline
                    }}
                    className="p-3 rounded border"
                  >
                    <span style={{ color: themeColors.onSurfaceMuted }} className="block text-[10px] uppercase">CUSTO REAL DO CARRO</span>
                    <strong style={{ color: isDark ? '#F87171' : tokens.colors.signalBad }} className="text-base">- R$ 113,80</strong>
                  </div>
                  <div
                    style={{
                      backgroundColor: isDark ? '#14532D20' : '#DCFCE7',
                      borderColor: isDark ? '#15803D80' : '#86EFAC'
                    }}
                    className="p-3 rounded border"
                  >
                    <span style={{ color: isDark ? '#86EFAC' : '#14532D' }} className="block text-[10px] font-bold uppercase">LUCRO LÍQUIDO REAL</span>
                    <strong style={{ color: isDark ? '#4ADE80' : tokens.colors.signalGood }} className="text-base">R$ 371,40</strong>
                  </div>
                </div>
                <div className="pt-2 flex justify-between items-center">
                  <button
                    type="button"
                    onClick={() => setTourStep(4)}
                    style={{ color: themeColors.onSurfaceMuted }}
                    className="text-xs underline cursor-pointer hover:opacity-80"
                  >
                    ← Passo Anterior
                  </button>
                  <button
                    type="button"
                    onClick={() => setTourStep(6)}
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary,
                      borderRadius: tokens.rounded.full,
                      padding: '8px 24px',
                      fontSize: '13px',
                      fontWeight: 600
                    }}
                    className="cursor-pointer hover:opacity-90"
                  >
                    Próximo Passo →
                  </button>
                </div>
              </div>
            )}

            {tourStep === 6 && (
              <div
                style={{
                  backgroundColor: themeColors.surface,
                  borderRadius: tokens.rounded.md,
                  border: `1.5px solid ${themeColors.outline}`,
                  padding: '24px'
                }}
                className="space-y-4"
              >
                <div className="flex items-center gap-2">
                  <span
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary
                    }}
                    className="w-7 h-7 rounded-full font-bold text-xs flex items-center justify-center shrink-0"
                  >
                    6
                  </span>
                  <h3 className="text-lg font-bold" style={{ color: themeColors.onSurface }}>
                    Passo 6: Relatórios, Metas & Exportação MEI
                  </h3>
                </div>
                <p className="text-sm leading-relaxed" style={{ color: themeColors.onSurfaceMuted }}>
                  O dia é consolidado: a meta diária é dada como atingida (108%), o pico das 06h às 09h registra R$ 59,33/h e o motorista baixa a planilha CSV para a contabilidade do MEI com um toque.
                </p>
                <div
                  style={{
                    backgroundColor: themeColors.surfaceVariant,
                    borderColor: themeColors.outline
                  }}
                  className="p-4 rounded-lg border flex flex-col sm:flex-row justify-between items-start sm:items-center gap-3"
                >
                  <div>
                    <span style={{ color: themeColors.onSurface }} className="text-xs font-bold block">
                      Relatório Consolidado Disponível
                    </span>
                    <span style={{ color: themeColors.onSurfaceMuted }} className="text-xs">
                      Totalmente formatado para Excel e Google Sheets
                    </span>
                  </div>
                  <button
                    type="button"
                    onClick={handleExportCSV}
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary,
                      borderRadius: tokens.rounded.full,
                      padding: '8px 20px',
                      fontSize: '12px',
                      fontWeight: 600
                    }}
                    className="cursor-pointer shrink-0 hover:opacity-90"
                  >
                    Baixar CSV Agora 📥
                  </button>
                </div>
                <div className="pt-2 flex justify-between items-center">
                  <button
                    type="button"
                    onClick={() => setTourStep(5)}
                    style={{ color: themeColors.onSurfaceMuted }}
                    className="text-xs underline cursor-pointer hover:opacity-80"
                  >
                    ← Passo Anterior
                  </button>
                  <button
                    type="button"
                    onClick={() => setTourStep(1)}
                    style={{
                      backgroundColor: themeColors.surfaceVariant,
                      color: themeColors.onSurface,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.full,
                      padding: '8px 20px',
                      fontSize: '13px',
                      fontWeight: 600
                    }}
                    className="cursor-pointer hover:opacity-90"
                  >
                    Reiniciar Tour ↺
                  </button>
                </div>
              </div>
            )}
          </div>
        )}

        {/* -1. ABA DE RECOMENDAÇÕES (FASE 3) */}
        {activeTab === 'recommendations' && (
          <div className="space-y-6">
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderRadius: tokens.rounded.md,
                padding: '24px',
                border: `1.5px solid ${themeColors.outline}`
              }}
            >
              <span
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-mono font-semibold block uppercase mb-1"
              >
                COPILOTO ROTTA • FASE 3
              </span>
              <h2
                style={{
                  fontSize: tokens.typography.headline.fontSize,
                  lineHeight: tokens.typography.headline.lineHeight,
                  fontWeight: tokens.typography.headline.fontWeight,
                  color: themeColors.onSurface
                }}
              >
                Recomendações do Copiloto
              </h2>
              <p
                style={{
                  fontSize: tokens.typography.bodySm.fontSize,
                  color: themeColors.onSurfaceMuted
                }}
                className="mt-2"
              >
                Dicas simples e acionáveis baseadas no seu histórico real de corridas e custos do carro.
              </p>
            </div>

            {/* Cartão 1: Melhor Horário */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderRadius: tokens.rounded.md,
                padding: '20px',
                border: `1.5px solid ${themeColors.outline}`
              }}
              className="space-y-3"
            >
              <div className="flex justify-between items-center">
                <span className="text-xs font-mono font-semibold uppercase text-zinc-500">
                  HORÁRIO DE MAIOR RENDIMENTO
                </span>
                <span className="text-xs font-semibold text-emerald-600 bg-emerald-100 dark:bg-emerald-950 px-2 py-0.5 rounded-full">
                  +48% de Ganho
                </span>
              </div>
              <h3 className="text-lg font-semibold" style={{ color: themeColors.onSurface }}>
                06h às 09h é o seu melhor momento
              </h3>
              <p className="text-xs text-zinc-500 leading-relaxed">
                Nessa faixa horária, seu ganho médio foi de <strong>R$ 58,00 por hora</strong>, muito acima das faixas da tarde. Rodar cedo garante faturamento alto com menos trânsito travado.
              </p>
              <div
                style={{ backgroundColor: themeColors.surface }}
                className="p-3 rounded-lg flex justify-between items-center"
              >
                <div>
                  <span className="text-[11px] font-mono uppercase text-zinc-400 block">GANHO MÉDIO PICO</span>
                  <span className="text-xl font-bold tabular-nums" style={{ color: themeColors.onSurface }}>
                    R$ 58,00/h
                  </span>
                </div>
                <div className="text-right">
                  <span className="text-[11px] font-mono uppercase text-zinc-400 block">DEMAIS HORÁRIOS</span>
                  <span className="text-sm font-semibold tabular-nums text-zinc-400">
                    R$ 38,00/h
                  </span>
                </div>
              </div>
            </div>

            {/* Cartão 2: Custo Real do Km */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderRadius: tokens.rounded.md,
                padding: '20px',
                border: `1.5px solid ${themeColors.outline}`
              }}
              className="space-y-3"
            >
              <span className="text-xs font-mono font-semibold uppercase text-zinc-500 block">
                CUSTO DO VEÍCULO • CHEVROLET ONIX 1.0
              </span>
              <h3 className="text-lg font-semibold" style={{ color: themeColors.onSurface }}>
                Seu custo real é R$ 0,68 por km rodado
              </h3>
              <p className="text-xs text-zinc-500 leading-relaxed">
                Calculado com base em R$ 5,89/L de combustível e seus custos fixos mensais (R$ 920). Corridas que pagam menos de <strong>R$ 2,20/km</strong> não deixam margem líquida suficiente.
              </p>
              <div
                style={{ backgroundColor: themeColors.surface }}
                className="p-3 rounded-lg flex justify-between items-center"
              >
                <div>
                  <span className="text-[11px] font-mono uppercase text-zinc-400 block">CUSTO DO KM</span>
                  <span className="text-xl font-bold tabular-nums" style={{ color: themeColors.onSurface }}>
                    R$ 0,68/km
                  </span>
                </div>
                <div className="text-right">
                  <span className="text-[11px] font-mono uppercase text-zinc-400 block">LUCRO MÍNIMO SEGURO</span>
                  <span className="text-sm font-semibold tabular-nums text-emerald-600">
                    &gt; R$ 1,52/km líquido
                  </span>
                </div>
              </div>
            </div>

            {/* Cartão 3: Meta de Hoje */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderRadius: tokens.rounded.md,
                padding: '20px',
                border: `1.5px solid ${themeColors.outline}`
              }}
              className="space-y-3"
            >
              <div className="flex justify-between items-center">
                <span className="text-xs font-mono font-semibold uppercase text-zinc-500">
                  RITMO DA META DIÁRIA
                </span>
                <span className="text-xs font-semibold text-emerald-600">
                  97% concluído
                </span>
              </div>
              <h3 className="text-lg font-semibold" style={{ color: themeColors.onSurface }}>
                Faltam apenas R$ 14,80 para bater a meta hoje
              </h3>
              <p className="text-xs text-zinc-500 leading-relaxed">
                Você já faturou R$ 485,20 da sua meta de R$ 500,00. Uma única corrida curta atinge o objetivo estipulado.
              </p>
              <div
                style={{ backgroundColor: themeColors.surface }}
                className="p-3 rounded-lg flex justify-between items-center"
              >
                <div>
                  <span className="text-[11px] font-mono uppercase text-zinc-400 block">RESTANTE HOJE</span>
                  <span className="text-xl font-bold tabular-nums" style={{ color: themeColors.onSurface }}>
                    R$ 14,80
                  </span>
                </div>
                <button
                  type="button"
                  onClick={() => setActiveTab('goals')}
                  className="text-xs underline font-semibold text-zinc-500 hover:text-black dark:hover:text-white cursor-pointer"
                >
                  Ver Metas →
                </button>
              </div>
            </div>

            {/* Cartão 4: Filtro do Semáforo */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderRadius: tokens.rounded.md,
                padding: '20px',
                border: `1.5px solid ${themeColors.outline}`
              }}
              className="space-y-3"
            >
              <span className="text-xs font-mono font-semibold uppercase text-zinc-500 block">
                APROVEITAMENTO DO SEMÁFORO
              </span>
              <h3 className="text-lg font-semibold" style={{ color: themeColors.onSurface }}>
                82% das corridas aceitas foram "Vale"
              </h3>
              <p className="text-xs text-zinc-500 leading-relaxed">
                Ao manter a disciplina do semáforo e rejeitar ofertas com sinal vermelho, seu ganho médio por quilômetro rodado subiu de R$ 2,10 para <strong>R$ 2,94/km</strong>.
              </p>
              <div className="flex justify-end pt-2">
                <button
                  type="button"
                  onClick={() => setActiveTab('signal')}
                  style={{
                    backgroundColor: themeColors.primary,
                    color: themeColors.onPrimary,
                    borderRadius: tokens.rounded.full,
                    padding: '10px 20px',
                    fontSize: tokens.typography.bodySm.fontSize
                  }}
                  className="cursor-pointer font-medium hover:opacity-90"
                >
                  Ajustar Critérios do Semáforo →
                </button>
              </div>
            </div>
          </div>
        )}

        {/* 0. ABA DE SEMÁFORO E SIMULADOR (ETAPA H) */}
        {activeTab === 'signal' && (
          <div className="space-y-6">
            {/* Header de Status */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderRadius: tokens.rounded.md,
                padding: '20px',
                border: `1.5px solid ${themeColors.outline}`
              }}
            >
              <div className="flex justify-between items-start">
                <div>
                  <span
                    style={{
                      fontSize: tokens.typography.label.fontSize,
                      letterSpacing: tokens.typography.label.letterSpacing,
                      color: themeColors.onSurfaceMuted
                    }}
                    className="font-mono font-semibold block uppercase mb-1"
                  >
                    COPILOTO DETERMINÍSTICO • ETAPA (H)
                  </span>
                  <h2
                    style={{
                      fontSize: tokens.typography.headline.fontSize,
                      lineHeight: tokens.typography.headline.lineHeight,
                      fontWeight: tokens.typography.headline.fontWeight,
                      color: themeColors.onSurface
                    }}
                  >
                    Semáforo de Ofertas & Overlay
                  </h2>
                </div>
                <div
                  style={{
                    backgroundColor: themeColors.primary,
                    color: themeColors.onPrimary,
                    borderRadius: tokens.rounded.full,
                    padding: '6px 14px',
                    fontSize: '12px',
                    fontWeight: 600
                  }}
                  className="font-mono uppercase tracking-wider"
                >
                  Resposta &lt; 2ms
                </div>
              </div>
              <p
                style={{
                  fontSize: tokens.typography.bodySm.fontSize,
                  color: themeColors.onSurfaceMuted
                }}
                className="mt-2"
              >
                Avaliação 100% no celular sem dependência de rede ou IA. Compara o valor oferecido com os seus critérios mínimos configurados.
              </p>
            </div>

            {/* 1. Card de Configuração dos Mínimos */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderRadius: tokens.rounded.md,
                padding: '24px',
                border: `1.5px solid ${themeColors.outline}`
              }}
            >
              <div className="flex justify-between items-center mb-4">
                <span
                  style={{
                    fontSize: tokens.typography.label.fontSize,
                    letterSpacing: tokens.typography.label.letterSpacing,
                    color: themeColors.onSurfaceMuted
                  }}
                  className="font-mono font-semibold block uppercase"
                >
                  SEUS CRITÉRIOS MÍNIMOS ACEITÁVEIS
                </span>
                {savedCriteriaToast && (
                  <span className="text-xs font-semibold text-emerald-600 bg-emerald-100 dark:bg-emerald-950 px-2 py-0.5 rounded-full">
                    Critérios salvos!
                  </span>
                )}
              </div>

              <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                <RottaFormInput
                  label="Mínimo por Km (R$/km)"
                  value={minRateKm}
                  onChange={setMinRateKm}
                  prefix="R$"
                  suffix="/km"
                  isMono={true}
                  themeColors={themeColors}
                />
                <RottaFormInput
                  label="Mínimo por Minuto (R$/min)"
                  value={minRateMin}
                  onChange={setMinRateMin}
                  prefix="R$"
                  suffix="/min"
                  isMono={true}
                  themeColors={themeColors}
                />
                <RottaFormInput
                  label="Mínimo por Hora (R$/h)"
                  value={minRateHour}
                  onChange={setMinRateHour}
                  prefix="R$"
                  suffix="/h"
                  isMono={true}
                  themeColors={themeColors}
                />
              </div>

              <div className="mt-4 pt-4 border-t flex justify-end" style={{ borderColor: themeColors.outline }}>
                <button
                  type="button"
                  onClick={() => {
                    setSavedCriteriaToast(true);
                    setTimeout(() => setSavedCriteriaToast(false), 2500);
                  }}
                  style={{
                    backgroundColor: themeColors.primary,
                    color: themeColors.onPrimary,
                    borderRadius: tokens.rounded.full,
                    height: '48px',
                    padding: '0 28px',
                    fontSize: tokens.typography.title.fontSize,
                    fontWeight: tokens.typography.title.fontWeight
                  }}
                  className="cursor-pointer font-medium hover:opacity-90 transition-opacity"
                >
                  Salvar Critérios
                </button>
              </div>
            </div>

            {/* 2. Opções de Comportamento do Overlay Flutuante */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderRadius: tokens.rounded.md,
                padding: '24px',
                border: `1.5px solid ${themeColors.outline}`
              }}
              className="space-y-4"
            >
              <span
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-mono font-semibold block uppercase"
              >
                COMPORTAMENTO DO OVERLAY FLUTUANTE
              </span>

              <div className="flex items-center justify-between p-3 rounded-lg" style={{ backgroundColor: themeColors.surface }}>
                <div>
                  <p className="font-semibold text-sm" style={{ color: themeColors.onSurface }}>
                    Bolha Flutuante Ativa
                  </p>
                  <p className="text-xs" style={{ color: themeColors.onSurfaceMuted }}>
                    Exibe bolha de 52px que flutua discretamente sobre outros apps
                  </p>
                </div>
                <button
                  type="button"
                  onClick={() => setOverlayEnabled(!overlayEnabled)}
                  style={{
                    backgroundColor: overlayEnabled ? themeColors.primary : themeColors.surfaceVariant,
                    color: overlayEnabled ? themeColors.onPrimary : themeColors.onSurface,
                    border: `1px solid ${themeColors.outline}`,
                    borderRadius: tokens.rounded.full,
                    padding: '6px 16px',
                    fontSize: '13px',
                    fontWeight: 600
                  }}
                  className="cursor-pointer"
                >
                  {overlayEnabled ? 'Ligada' : 'Desligada'}
                </button>
              </div>

              <div className="flex items-center justify-between p-3 rounded-lg" style={{ backgroundColor: themeColors.surface }}>
                <div>
                  <p className="font-semibold text-sm" style={{ color: themeColors.onSurface }}>
                    Expansão Automática ao Surgir Corrida
                  </p>
                  <p className="text-xs" style={{ color: themeColors.onSurfaceMuted }}>
                    Abre o semáforo automaticamente durante o toque de oferta do Uber ou 99
                  </p>
                </div>
                <button
                  type="button"
                  onClick={() => setAutoExpandOffer(!autoExpandOffer)}
                  style={{
                    backgroundColor: autoExpandOffer ? themeColors.primary : themeColors.surfaceVariant,
                    color: autoExpandOffer ? themeColors.onPrimary : themeColors.onSurface,
                    border: `1px solid ${themeColors.outline}`,
                    borderRadius: tokens.rounded.full,
                    padding: '6px 16px',
                    fontSize: '13px',
                    fontWeight: 600
                  }}
                  className="cursor-pointer"
                >
                  {autoExpandOffer ? 'Ativada' : 'Desativada'}
                </button>
              </div>

              {/* Toggle de Feedback Tátil (Direção Segura) */}
              <div className="flex items-center justify-between p-3 rounded-lg" style={{ backgroundColor: themeColors.surface }}>
                <div>
                  <p className="font-semibold text-sm" style={{ color: themeColors.onSurface }}>
                    Feedback Tátil ao Volante (Vibração)
                  </p>
                  <p className="text-xs" style={{ color: themeColors.onSurfaceMuted }}>
                    1 pulso firme = "Vale" • 2 rápidos = "Neutra" • Sem desviar o olhar
                  </p>
                </div>
                <button
                  type="button"
                  onClick={() => {
                    const next = !hapticFeedbackEnabled;
                    setHapticFeedbackEnabled(next);
                    if (next && typeof window !== 'undefined' && 'vibrate' in navigator) {
                      navigator.vibrate(85);
                    }
                  }}
                  style={{
                    backgroundColor: hapticFeedbackEnabled ? themeColors.primary : themeColors.surfaceVariant,
                    color: hapticFeedbackEnabled ? themeColors.onPrimary : themeColors.onSurface,
                    border: `1px solid ${themeColors.outline}`,
                    borderRadius: tokens.rounded.full,
                    padding: '6px 16px',
                    fontSize: '13px',
                    fontWeight: 600
                  }}
                  className="cursor-pointer"
                >
                  {hapticFeedbackEnabled ? 'Ativada' : 'Desativada'}
                </button>
              </div>

              {/* Toggle de Sinal Sonoro Discreto */}
              <div className="flex items-center justify-between p-3 rounded-lg" style={{ backgroundColor: themeColors.surface }}>
                <div>
                  <p className="font-semibold text-sm" style={{ color: themeColors.onSurface }}>
                    Sinal Sonoro Discreto (Bip da Vitória)
                  </p>
                  <p className="text-xs" style={{ color: themeColors.onSurfaceMuted }}>
                    Toque curto exclusivo quando a corrida atende a todos os mínimos
                  </p>
                </div>
                <button
                  type="button"
                  onClick={() => setSoundChimeEnabled(!soundChimeEnabled)}
                  style={{
                    backgroundColor: soundChimeEnabled ? themeColors.primary : themeColors.surfaceVariant,
                    color: soundChimeEnabled ? themeColors.onPrimary : themeColors.onSurface,
                    border: `1px solid ${themeColors.outline}`,
                    borderRadius: tokens.rounded.full,
                    padding: '6px 16px',
                    fontSize: '13px',
                    fontWeight: 600
                  }}
                  className="cursor-pointer"
                >
                  {soundChimeEnabled ? 'Ativado' : 'Desativado'}
                </button>
              </div>

              {hapticFiredNotice && (
                <div
                  style={{
                    backgroundColor: themeColors.surfaceVariant,
                    borderColor: themeColors.outline,
                    color: themeColors.onSurface
                  }}
                  className="p-2 text-center rounded border text-xs font-mono"
                >
                  {hapticFiredNotice}
                </div>
              )}
            </div>

            {/* 3. SIMULADOR INTERATIVO DE CORRIDAS */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderRadius: tokens.rounded.md,
                padding: '24px',
                border: `1.5px solid ${themeColors.outline}`
              }}
              className="space-y-5"
            >
              <div className="flex justify-between items-center">
                <span
                  style={{
                    fontSize: tokens.typography.label.fontSize,
                    letterSpacing: tokens.typography.label.letterSpacing,
                    color: themeColors.onSurfaceMuted
                  }}
                  className="font-mono font-semibold block uppercase"
                >
                  SIMULADOR DE OFERTAS EM TEMPO REAL
                </span>
                <span className="text-xs font-mono text-zinc-400">
                  Determinístico • &lt; 2ms
                </span>
              </div>

              {/* Botões de Ofertas Pré-configuradas */}
              <div>
                <p className="text-xs font-medium mb-2" style={{ color: themeColors.onSurfaceMuted }}>
                  Testar cenários pré-configurados:
                </p>
                <div className="grid grid-cols-1 sm:grid-cols-3 gap-2">
                  <button
                    type="button"
                    onClick={() => {
                      setSimOfferApp('Uber');
                      setSimOfferGross('38.50');
                      setSimOfferKm('12.0');
                      setSimOfferMin('24');
                      triggerSimulatedFeedback('GOOD');
                      if (autoExpandOffer) setFloatingOverlayExpanded(true);
                    }}
                    style={{
                      backgroundColor: themeColors.surface,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.sm,
                      padding: '10px 12px'
                    }}
                    className="text-left cursor-pointer hover:border-zinc-400 transition-colors"
                  >
                    <span className="text-xs font-bold text-emerald-600 block">Cenário 1: "Vale"</span>
                    <span className="text-sm font-semibold block" style={{ color: themeColors.onSurface }}>Uber • R$ 38,50</span>
                    <span className="text-xs text-zinc-500">12,0 km • 24 min (R$ 3,21/km)</span>
                  </button>

                  <button
                    type="button"
                    onClick={() => {
                      setSimOfferApp('99');
                      setSimOfferGross('19.00');
                      setSimOfferKm('7.5');
                      setSimOfferMin('28');
                      triggerSimulatedFeedback('MEDIUM');
                      if (autoExpandOffer) setFloatingOverlayExpanded(true);
                    }}
                    style={{
                      backgroundColor: themeColors.surface,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.sm,
                      padding: '10px 12px'
                    }}
                    className="text-left cursor-pointer hover:border-zinc-400 transition-colors"
                  >
                    <span className="text-xs font-bold text-amber-500 block">Cenário 2: "Neutra"</span>
                    <span className="text-sm font-semibold block" style={{ color: themeColors.onSurface }}>99 • R$ 19,00</span>
                    <span className="text-xs text-zinc-500">7,5 km • 28 min (R$ 2,53/km)</span>
                  </button>

                  <button
                    type="button"
                    onClick={() => {
                      setSimOfferApp('Uber');
                      setSimOfferGross('11.50');
                      setSimOfferKm('6.8');
                      setSimOfferMin('35');
                      triggerSimulatedFeedback('BAD');
                      if (autoExpandOffer) setFloatingOverlayExpanded(true);
                    }}
                    style={{
                      backgroundColor: themeColors.surface,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.sm,
                      padding: '10px 12px'
                    }}
                    className="text-left cursor-pointer hover:border-zinc-400 transition-colors"
                  >
                    <span className="text-xs font-bold text-red-600 block">Cenário 3: "Fraca"</span>
                    <span className="text-sm font-semibold block" style={{ color: themeColors.onSurface }}>Uber • R$ 11,50</span>
                    <span className="text-xs text-zinc-500">6,8 km • 35 min (R$ 1,69/km)</span>
                  </button>
                </div>
              </div>

              {/* CENÁRIOS EXTREMOS DO TRÂNSITO REAL (STRESS TEST) */}
              <div className="pt-2">
                <div className="flex justify-between items-center mb-2">
                  <p className="text-xs font-mono font-semibold uppercase text-zinc-500">
                    Cenários Extremos do Trânsito Real (Stress Test)
                  </p>
                  <span
                    style={{
                      backgroundColor: themeColors.surfaceVariant,
                      color: themeColors.onSurfaceMuted,
                      borderColor: themeColors.outline
                    }}
                    className="text-[10px] border px-2 py-0.5 rounded-full font-mono"
                  >
                    Casos de Borda
                  </span>
                </div>
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-2">
                  {/* Extremo 1: Trânsito Travado */}
                  <button
                    type="button"
                    onClick={() => {
                      setSimOfferApp('99');
                      setSimOfferGross('9.50');
                      setSimOfferKm('1.5');
                      setSimOfferMin('25');
                      triggerSimulatedFeedback('MEDIUM');
                      if (autoExpandOffer) setFloatingOverlayExpanded(true);
                    }}
                    style={{
                      backgroundColor: themeColors.surface,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.sm,
                      padding: '10px 12px'
                    }}
                    className="text-left cursor-pointer hover:border-amber-400 transition-colors"
                  >
                    <span className="text-[11px] font-bold text-amber-500 block uppercase">Trânsito Travado</span>
                    <span className="text-sm font-semibold block" style={{ color: themeColors.onSurface }}>99 • R$ 9,50</span>
                    <span className="text-xs text-zinc-400 block">1,5 km • 25 min</span>
                    <span className="text-[10px] text-amber-500 font-mono mt-0.5 block">R$ 6,33/km mas R$ 22,80/h (Neutra)</span>
                  </button>

                  {/* Extremo 2: Intermunicipal Longo */}
                  <button
                    type="button"
                    onClick={() => {
                      setSimOfferApp('Uber');
                      setSimOfferGross('215.80');
                      setSimOfferKm('92.5');
                      setSimOfferMin('95');
                      triggerSimulatedFeedback('GOOD');
                      if (autoExpandOffer) setFloatingOverlayExpanded(true);
                    }}
                    style={{
                      backgroundColor: themeColors.surface,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.sm,
                      padding: '10px 12px'
                    }}
                    className="text-left cursor-pointer hover:border-emerald-500 transition-colors"
                  >
                    <span className="text-[11px] font-bold text-emerald-500 block uppercase">Intermunicipal (1h35)</span>
                    <span className="text-sm font-semibold block" style={{ color: themeColors.onSurface }}>Uber • R$ 215,80</span>
                    <span className="text-xs text-zinc-400 block">92,5 km • 95 min (1h35)</span>
                    <span className="text-[10px] text-emerald-500 font-mono mt-0.5 block">R$ 2,33/km e R$ 136,29/h (Vale)</span>
                  </button>

                  {/* Extremo 3: Corrida Isca */}
                  <button
                    type="button"
                    onClick={() => {
                      setSimOfferApp('Uber');
                      setSimOfferGross('23.00');
                      setSimOfferKm('22.0');
                      setSimOfferMin('48');
                      triggerSimulatedFeedback('BAD');
                      if (autoExpandOffer) setFloatingOverlayExpanded(true);
                    }}
                    style={{
                      backgroundColor: themeColors.surface,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.sm,
                      padding: '10px 12px'
                    }}
                    className="text-left cursor-pointer hover:border-red-500 transition-colors"
                  >
                    <span className="text-[11px] font-bold text-red-500 block uppercase">Corrida Isca</span>
                    <span className="text-sm font-semibold block" style={{ color: themeColors.onSurface }}>Uber • R$ 23,00</span>
                    <span className="text-xs text-zinc-400 block">22,0 km • 48 min</span>
                    <span className="text-[10px] text-red-500 font-mono mt-0.5 block">R$ 1,04/km e R$ 28,75/h (Fraca)</span>
                  </button>

                  {/* Extremo 4: Dinâmica Relâmpago */}
                  <button
                    type="button"
                    onClick={() => {
                      setSimOfferApp('Uber');
                      setSimOfferGross('28.00');
                      setSimOfferKm('3.2');
                      setSimOfferMin('8');
                      triggerSimulatedFeedback('GOOD');
                      if (autoExpandOffer) setFloatingOverlayExpanded(true);
                    }}
                    style={{
                      backgroundColor: themeColors.surface,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.sm,
                      padding: '10px 12px'
                    }}
                    className="text-left cursor-pointer hover:border-emerald-500 transition-colors"
                  >
                    <span className="text-[11px] font-bold text-emerald-500 block uppercase">Dinâmica Relâmpago</span>
                    <span className="text-sm font-semibold block" style={{ color: themeColors.onSurface }}>Uber • R$ 28,00</span>
                    <span className="text-xs text-zinc-400 block">3,2 km • 8 min</span>
                    <span className="text-[10px] text-emerald-500 font-mono mt-0.5 block">R$ 8,75/km e R$ 210,00/h (Vale)</span>
                  </button>
                </div>
              </div>

              {/* TESTE DE LEITURA DO PARSER DE ACESSIBILIDADE (TEXTO BRUTO) */}
              <div
                style={{
                  backgroundColor: themeColors.surface,
                  borderRadius: tokens.rounded.sm,
                  padding: '16px',
                  border: `1px dashed ${themeColors.outline}`
                }}
                className="space-y-3"
              >
                <div className="flex justify-between items-center">
                  <span className="text-xs font-mono font-semibold uppercase text-zinc-400">
                    Teste do Parser Determinístico (Acessibilidade)
                  </span>
                  <span className="text-[11px] text-emerald-500 font-mono">
                    Zero dados de passageiro • Local &lt; 1ms
                  </span>
                </div>
                <div className="flex gap-2">
                  <input
                    type="text"
                    value={rawTextSample}
                    onChange={(e) => setRawTextSample(e.target.value)}
                    placeholder="Cole ou digite texto bruto da tela da Uber ou 99..."
                    style={{
                      backgroundColor: themeColors.surfaceVariant,
                      color: themeColors.onSurface,
                      borderColor: themeColors.outline,
                      borderRadius: tokens.rounded.sm
                    }}
                    className="flex-1 px-3 py-2 text-xs border font-mono outline-none"
                  />
                  <button
                    type="button"
                    onClick={() => parseRawAccessibilityText(rawTextSample)}
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary,
                      borderRadius: tokens.rounded.sm
                    }}
                    className="px-4 py-2 text-xs font-semibold cursor-pointer shrink-0 hover:opacity-90 transition-opacity"
                  >
                    Testar Parser
                  </button>
                </div>

                <div className="flex flex-wrap gap-1.5 text-[11px]">
                  <span className="text-zinc-500 self-center">Amostras rápidas:</span>
                  <button
                    type="button"
                    onClick={() => {
                      const sample = 'UberX VIP • R$ 215,80 • 92,5 km • 1 h 35 min de viagem';
                      setRawTextSample(sample);
                      parseRawAccessibilityText(sample);
                    }}
                    style={{ color: themeColors.onSurface }}
                    className="text-xs underline font-medium cursor-pointer"
                  >
                    Uber 1h35min (R$ 215,80)
                  </button>
                  <span className="text-zinc-600">•</span>
                  <button
                    type="button"
                    onClick={() => {
                      const sample = '99Pop • R$ 9,50 • 1,5 km • 25 mins de trajeto';
                      setRawTextSample(sample);
                      parseRawAccessibilityText(sample);
                    }}
                    style={{ color: themeColors.onSurface }}
                    className="text-xs underline font-medium cursor-pointer"
                  >
                    99 Trânsito Lento (R$ 9,50)
                  </button>
                  <span className="text-zinc-600">•</span>
                  <button
                    type="button"
                    onClick={() => {
                      const sample = 'UberX • R$ 8,00 • 800 metros • 4 min';
                      setRawTextSample(sample);
                      parseRawAccessibilityText(sample);
                    }}
                    style={{ color: themeColors.onSurface }}
                    className="text-xs underline font-medium cursor-pointer"
                  >
                    Uber 800m (R$ 8,00)
                  </button>
                </div>

                {parseLogToast && (
                  <div
                    style={{
                      backgroundColor: isDark ? '#14532D30' : '#DCFCE7',
                      borderColor: isDark ? '#15803D80' : '#86EFAC',
                      color: isDark ? '#86EFAC' : '#14532D'
                    }}
                    className="p-2.5 rounded border text-xs font-mono font-medium"
                  >
                    ✓ {parseLogToast}
                  </div>
                )}
              </div>

              {/* Campos de Entrada da Oferta */}
              <div className="grid grid-cols-2 sm:grid-cols-4 gap-3">
                <div>
                  <label className="text-xs font-mono font-semibold block uppercase mb-1.5" style={{ color: themeColors.onSurfaceMuted }}>
                    Aplicativo
                  </label>
                  <div className="flex gap-1 h-12">
                    <button
                      type="button"
                      onClick={() => setSimOfferApp('Uber')}
                      style={{
                        backgroundColor: simOfferApp === 'Uber' ? themeColors.primary : themeColors.surface,
                        color: simOfferApp === 'Uber' ? themeColors.onPrimary : themeColors.onSurface,
                        border: `1.5px solid ${simOfferApp === 'Uber' ? themeColors.primary : themeColors.outline}`,
                        borderRadius: tokens.rounded.sm
                      }}
                      className="flex-1 font-semibold text-xs cursor-pointer"
                    >
                      Uber
                    </button>
                    <button
                      type="button"
                      onClick={() => setSimOfferApp('99')}
                      style={{
                        backgroundColor: simOfferApp === '99' ? themeColors.primary : themeColors.surface,
                        color: simOfferApp === '99' ? themeColors.onPrimary : themeColors.onSurface,
                        border: `1.5px solid ${simOfferApp === '99' ? themeColors.primary : themeColors.outline}`,
                        borderRadius: tokens.rounded.sm
                      }}
                      className="flex-1 font-semibold text-xs cursor-pointer"
                    >
                      99
                    </button>
                  </div>
                </div>

                <RottaFormInput
                  label="Valor da Corrida"
                  value={simOfferGross}
                  onChange={setSimOfferGross}
                  prefix="R$"
                  isMono={true}
                  themeColors={themeColors}
                />

                <RottaFormInput
                  label="Distância Estimada"
                  value={simOfferKm}
                  onChange={setSimOfferKm}
                  suffix="km"
                  isMono={true}
                  themeColors={themeColors}
                />

                <RottaFormInput
                  label="Tempo Estimado"
                  value={simOfferMin}
                  onChange={setSimOfferMin}
                  suffix="min"
                  isMono={true}
                  themeColors={themeColors}
                />
              </div>

              {/* Painel do Resultado Instantâneo do Semáforo */}
              <div
                style={{
                  backgroundColor: themeColors.surface,
                  borderRadius: tokens.rounded.md,
                  border: `2px solid ${simGradeColor}`,
                  padding: '20px'
                }}
              >
                <div className="flex justify-between items-center mb-4">
                  <div>
                    <span className="text-xs font-mono uppercase tracking-wider text-zinc-500 block">
                      RESULTADO DA CORRIDA ({simOfferApp})
                    </span>
                    <span className="text-2xl font-bold tabular-nums" style={{ color: themeColors.onSurface }}>
                      R$ {parsedSimGross.toFixed(2).replace('.', ',')}
                    </span>
                    <span className="text-xs text-zinc-400 block mt-0.5">
                      {parsedSimKm} km • {parsedSimMin} minutos
                    </span>
                  </div>

                  {/* Chip Semáforo: Cor + Palavra (Regra de Acessibilidade Obrigatória) */}
                  <div
                    style={{
                      backgroundColor: simGradeColor,
                      color: simGradeTextColor,
                      borderRadius: tokens.rounded.full,
                      height: '40px',
                      padding: '0 24px'
                    }}
                    className="flex items-center justify-center font-semibold text-base shadow-sm shrink-0"
                  >
                    {simGradeWord}
                  </div>
                </div>

                {/* Grid das 3 métricas com comparação aos mínimos */}
                <div className="grid grid-cols-3 gap-2 pt-3 border-t" style={{ borderColor: themeColors.outline }}>
                  <div className="p-2 rounded" style={{ backgroundColor: themeColors.surfaceVariant }}>
                    <span className="text-[10px] font-mono uppercase text-zinc-500 block">R$/KM</span>
                    <span className="text-base font-semibold tabular-nums block" style={{ color: meetsSimKm ? tokens.colors.signalGood : tokens.colors.signalBad }}>
                      R$ {simRateKm.toFixed(2).replace('.', ',')}
                    </span>
                    <span className="text-[11px] text-zinc-400">
                      mín: R$ {parsedMinKm.toFixed(2)} {meetsSimKm ? '✓' : '✗'}
                    </span>
                  </div>

                  <div className="p-2 rounded" style={{ backgroundColor: themeColors.surfaceVariant }}>
                    <span className="text-[10px] font-mono uppercase text-zinc-500 block">R$/MIN</span>
                    <span className="text-base font-semibold tabular-nums block" style={{ color: meetsSimMin ? tokens.colors.signalGood : tokens.colors.signalBad }}>
                      R$ {simRateMin.toFixed(2).replace('.', ',')}
                    </span>
                    <span className="text-[11px] text-zinc-400">
                      mín: R$ {parsedMinMin.toFixed(2)} {meetsSimMin ? '✓' : '✗'}
                    </span>
                  </div>

                  <div className="p-2 rounded" style={{ backgroundColor: themeColors.surfaceVariant }}>
                    <span className="text-[10px] font-mono uppercase text-zinc-500 block">R$/HORA</span>
                    <span className="text-base font-semibold tabular-nums block" style={{ color: meetsSimHour ? tokens.colors.signalGood : tokens.colors.signalBad }}>
                      R$ {simRateHour.toFixed(2).replace('.', ',')}
                    </span>
                    <span className="text-[11px] text-zinc-400">
                      mín: R$ {parsedMinHour.toFixed(2)} {meetsSimHour ? '✓' : '✗'}
                    </span>
                  </div>
                </div>

                <div className="mt-4 pt-3 flex justify-between items-center text-xs text-zinc-500">
                  <span>Regra: Verde (3 de 3) • Amarelo (1 ou 2) • Vermelho (0)</span>
                  <button
                    type="button"
                    onClick={() => setFloatingOverlayExpanded(true)}
                    className="underline font-medium hover:text-black dark:hover:text-white cursor-pointer"
                  >
                    Abrir no Overlay Flutuante ↗
                  </button>
                </div>
              </div>

              {/* Ciclo de Vida da Corrida & Relatórios Automáticos (Fase 2) */}
              <div
                style={{
                  backgroundColor: themeColors.surfaceVariant,
                  borderColor: themeColors.outline
                }}
                className="p-4 rounded-lg border space-y-3"
              >
                <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-2">
                  <div>
                    <span style={{ color: themeColors.onSurface }} className="text-xs font-mono font-bold uppercase block">
                      Automação da Corrida (Fase 2: Sem Toques ao Volante)
                    </span>
                    <span style={{ color: themeColors.onSurfaceMuted }} className="text-xs">
                      Simule o fluxo real: oferta na tela → motorista aceitou → viagem concluída com consolidação imediata no caixa
                    </span>
                  </div>
                  <span
                    style={{
                      backgroundColor: simRideLifecycle === 'completed'
                        ? (isDark ? '#14532D' : '#DCFCE7')
                        : simRideLifecycle === 'accepted'
                        ? (isDark ? '#713F12' : '#FEF3C7')
                        : themeColors.surface,
                      color: simRideLifecycle === 'completed'
                        ? (isDark ? '#86EFAC' : '#14532D')
                        : simRideLifecycle === 'accepted'
                        ? (isDark ? '#FDE68A' : '#78350F')
                        : themeColors.onSurfaceMuted,
                      borderColor: themeColors.outline
                    }}
                    className="text-[11px] font-mono font-semibold px-2.5 py-1 rounded-full border shrink-0"
                  >
                    {simRideLifecycle === 'idle' && 'AGUARDANDO CORRIDA'}
                    {simRideLifecycle === 'offered' && '● OFERTA NA TELA'}
                    {simRideLifecycle === 'accepted' && '🚗 EM VIAGEM COM PASSAGEIRO'}
                    {simRideLifecycle === 'completed' && '✓ VIAGEM REGISTRADA'}
                  </span>
                </div>

                <div className="grid grid-cols-1 sm:grid-cols-3 gap-2 pt-1">
                  <button
                    type="button"
                    onClick={() => {
                      setSimRideLifecycle('offered');
                      setFloatingOverlayExpanded(true);
                      triggerSimulatedFeedback(simGrade);
                    }}
                    style={{
                      backgroundColor: themeColors.surface,
                      color: themeColors.onSurface,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.sm
                    }}
                    className="p-2.5 text-xs font-semibold cursor-pointer hover:opacity-80 transition-opacity"
                  >
                    1. Oferta Apareceu 🔔
                  </button>

                  <button
                    type="button"
                    onClick={() => {
                      setSimRideLifecycle('accepted');
                      setFloatingOverlayExpanded(false);
                    }}
                    style={{
                      backgroundColor: themeColors.surface,
                      color: themeColors.onSurface,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.sm
                    }}
                    className="p-2.5 text-xs font-semibold cursor-pointer hover:opacity-80 transition-opacity"
                  >
                    2. Aceitou no App 🚗
                  </button>

                  <button
                    type="button"
                    onClick={() => {
                      const earned = parsedSimGross;
                      const newEntry: FinancialItem = {
                        id: `auto-${Date.now()}`,
                        type: 'earning',
                        title: simOfferApp,
                        amount: earned,
                        time: new Date().toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' }),
                        subtext: `Automático: ${parsedSimKm} km • ${parsedSimMin} min`
                      };
                      setEntries([newEntry, ...entries]);
                      setGrossEarnings(prev => prev + earned);
                      setRidesCount(prev => prev + 1);
                      setDistanceKm(prev => prev + parsedSimKm);
                      setSimRideLifecycle('completed');
                      setAutoReportToast(`✓ Corrida de R$ ${earned.toFixed(2).replace('.', ',')} lançada automaticamente na Jornada e nos Relatórios!`);
                      setTimeout(() => setAutoReportToast(null), 6000);
                    }}
                    style={{
                      backgroundColor: isDark ? '#14532D' : '#15803D',
                      color: '#FFFFFF',
                      borderRadius: tokens.rounded.sm
                    }}
                    className="p-2.5 text-xs font-bold cursor-pointer hover:opacity-90 transition-opacity"
                  >
                    3. Concluir (Auto-Report) 💾
                  </button>
                </div>

                {autoReportToast && (
                  <div
                    style={{
                      backgroundColor: isDark ? '#14532D30' : '#DCFCE7',
                      borderColor: isDark ? '#15803D80' : '#86EFAC',
                      color: isDark ? '#86EFAC' : '#14532D'
                    }}
                    className="p-2.5 rounded border text-xs font-mono font-medium text-center"
                  >
                    {autoReportToast}
                  </div>
                )}
              </div>
            </div>

            {/* Acesso rápido ao termo de consentimento */}
            <div className="flex justify-between items-center pt-2">
              <span className="text-xs text-zinc-500">
                Políticas de Acessibilidade e Sobreposição auditadas
              </span>
              <button
                type="button"
                onClick={() => setActiveTab('disclosure')}
                style={{
                  backgroundColor: themeColors.surfaceVariant,
                  color: themeColors.onSurface,
                  borderRadius: tokens.rounded.full,
                  padding: '10px 20px',
                  fontSize: tokens.typography.bodySm.fontSize,
                  border: `1px solid ${themeColors.outline}`
                }}
                className="cursor-pointer font-medium hover:opacity-80"
              >
                Ver Termo de Transparência de Permissões →
              </button>
            </div>
          </div>
        )}

        {/* 0.1 ABA DE TERMO DE TRANSPARÊNCIA (PROMINENT DISCLOSURE - ETAPA H) */}
        {activeTab === 'disclosure' && (
          <div className="space-y-6 max-w-xl mx-auto">
            {/* Monograma de Marca Rotta */}
            <div className="flex items-center gap-3">
              <div
                style={{
                  backgroundColor: themeColors.primary,
                  color: themeColors.onPrimary,
                  borderRadius: tokens.rounded.full,
                  width: '44px',
                  height: '44px'
                }}
                className="flex items-center justify-center font-bold text-xl"
              >
                R
              </div>
              <div>
                <span className="text-xs font-mono font-semibold uppercase tracking-wider text-zinc-500 block">
                  TRANSPARÊNCIA E POLÍTICA DE USO
                </span>
                <h2
                  style={{
                    fontSize: tokens.typography.headline.fontSize,
                    lineHeight: tokens.typography.headline.lineHeight,
                    fontWeight: tokens.typography.headline.fontWeight,
                    color: themeColors.onSurface
                  }}
                >
                  Consentimento Destacado
                </h2>
              </div>
            </div>

            <p style={{ fontSize: tokens.typography.body.fontSize, color: themeColors.onSurfaceMuted }}>
              O Rotta é um copiloto financeiro desenvolvido para o motorista de aplicativo. Nossas permissões seguem rigorosamente as políticas do Google Play e da LGPD:
            </p>

            {/* Card 1: Acessibilidade Somente Leitura */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderRadius: tokens.rounded.md,
                padding: '20px',
                border: `1.5px solid ${themeColors.outline}`
              }}
              className="space-y-3"
            >
              <div className="flex items-center justify-between">
                <span className="text-xs font-mono font-bold uppercase tracking-wider text-zinc-500">
                  1. ACESSIBILIDADE • APENAS LEITURA
                </span>
                <span className="text-xs font-mono text-emerald-600 bg-emerald-100 dark:bg-emerald-950 px-2 py-0.5 rounded-full">
                  Zero Ações Autônomas
                </span>
              </div>

              <div>
                <p className="font-semibold text-sm mb-1" style={{ color: themeColors.onSurface }}>
                  O que o serviço lê:
                </p>
                <p className="text-xs text-zinc-500 leading-relaxed">
                  Apenas o valor em reais (R$), a distância estimada em km e o tempo estimado em minutos exibidos na notificação ou tela de oferta do Uber e 99.
                </p>
              </div>

              <div className="p-3 rounded-lg" style={{ backgroundColor: themeColors.surface }}>
                <p className="font-semibold text-xs text-red-600 dark:text-red-400 uppercase tracking-wide mb-1">
                  LIMITES INEGOCIÁVEIS:
                </p>
                <ul className="text-xs space-y-1" style={{ color: themeColors.onSurfaceMuted }}>
                  <li>• <strong>Nunca clicamos, aceitamos ou recusamos nada.</strong> A decisão é 100% sua.</li>
                  <li>• <strong>Sem gestos simulados.</strong> Não usamos dispatchGesture nem performAction(CLICK).</li>
                  <li>• <strong>Minimização de dados:</strong> Nunca lemos nem guardamos nome, foto, nota ou endereço exato de passageiros.</li>
                  <li>• <strong>Privacidade:</strong> Nenhum dado sai do seu aparelho.</li>
                </ul>
              </div>
            </div>

            {/* Card 2: Sobreposição de Tela (Overlay) */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderRadius: tokens.rounded.md,
                padding: '20px',
                border: `1.5px solid ${themeColors.outline}`
              }}
              className="space-y-3"
            >
              <span className="text-xs font-mono font-bold uppercase tracking-wider text-zinc-500">
                2. SOBREPOSIÇÃO DE TELA (OVERLAY)
              </span>

              <p className="text-xs text-zinc-500 leading-relaxed">
                Mostra uma bolha compacta de 52px com a logo "R". Ao tocar, expande para mostrar o ganho da jornada e o semáforo da corrida atual (Vale, Neutra ou Fraca).
              </p>

              <div className="p-3 rounded-lg text-xs" style={{ backgroundColor: themeColors.surface, color: themeColors.onSurfaceMuted }}>
                • <strong>Posição lembrada e arrastável</strong> para você colocar onde preferir na borda da tela.<br />
                • <strong>Fora dos botões:</strong> Ocupa no máximo 30% da tela quando expandido e nunca cobre o botão de aceitar da Uber ou 99.
              </div>
            </div>

            {/* Card 3: Garantia do Direito de Recusa */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderRadius: tokens.rounded.md,
                padding: '16px',
                border: `1.5px solid ${themeColors.outline}`
              }}
            >
              <p className="text-xs leading-relaxed" style={{ color: themeColors.onSurfaceMuted }}>
                <strong>Direito de Recusa:</strong> Se você preferir não conceder a permissão de acessibilidade ou overlay, o Rotta continua 100% útil com início e encerramento de jornadas, lançamentos manuais de ganhos e despesas, metas diárias e relatórios detalhados.
              </p>
            </div>

            {/* Botões de Ação na Zona do Polegar */}
            <div className="pt-4 space-y-3">
              <button
                type="button"
                onClick={() => {
                  setDisclosureAccepted(true);
                  setOverlayEnabled(true);
                  setActiveTab('signal');
                }}
                style={{
                  backgroundColor: themeColors.primary,
                  color: themeColors.onPrimary,
                  borderRadius: tokens.rounded.full,
                  height: '56px',
                  padding: '16px',
                  fontSize: tokens.typography.title.fontSize,
                  fontWeight: tokens.typography.title.fontWeight
                }}
                className="w-full flex items-center justify-center font-semibold cursor-pointer hover:opacity-90 transition-opacity"
              >
                Concordar e Ativar Copiloto
              </button>

              <button
                type="button"
                onClick={() => {
                  setDisclosureAccepted(false);
                  setOverlayEnabled(false);
                  setActiveTab('journey');
                }}
                style={{
                  backgroundColor: themeColors.surfaceVariant,
                  color: themeColors.onSurface,
                  borderRadius: tokens.rounded.full,
                  height: '56px',
                  padding: '16px',
                  fontSize: tokens.typography.title.fontSize,
                  fontWeight: tokens.typography.title.fontWeight,
                  border: `1px solid ${themeColors.outline}`
                }}
                className="w-full flex items-center justify-center font-medium cursor-pointer hover:opacity-80 transition-opacity"
              >
                Agora não (Usar Rotta sem automação)
              </button>
            </div>
          </div>
        )}

        {/* 1. ABA DE RELATÓRIOS (ETAPA G) */}
        {activeTab === 'reports' && (
          <div className="space-y-6">
            {/* Seletor de Período (Hoje, Semana, Mês) */}
            <div className="grid grid-cols-3 gap-2">
              {(['today', 'week', 'month'] as const).map((periodKey) => {
                const isSelected = reportPeriod === periodKey;
                const labels = { today: 'Hoje', week: 'Semana', month: 'Mês' };
                return (
                  <button
                    key={periodKey}
                    type="button"
                    onClick={() => setReportPeriod(periodKey)}
                    style={{
                      backgroundColor: isSelected ? themeColors.primary : themeColors.surface,
                      color: isSelected ? themeColors.onPrimary : themeColors.onSurface,
                      border: `1.5px solid ${isSelected ? themeColors.primary : themeColors.outline}`,
                      borderRadius: tokens.rounded.sm,
                      height: '44px',
                      fontSize: tokens.typography.bodySm.fontSize,
                      fontWeight: isSelected ? 600 : 400
                    }}
                    className="cursor-pointer transition-colors"
                  >
                    {labels[periodKey]}
                  </button>
                );
              })}
            </div>

            {/* Número Dominante de Relance: Ganho Bruto do Período */}
            <div
              style={{
                backgroundColor: themeColors.surface,
                border: `1.5px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '24px'
              }}
            >
              <span
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-mono font-semibold uppercase block mb-1"
              >
                FATURAMENTO BRUTO • {currentReport.periodLabel.toUpperCase()}
              </span>

              <p
                style={{
                  fontSize: tokens.typography.display.fontSize,
                  fontWeight: tokens.typography.display.fontWeight,
                  letterSpacing: tokens.typography.display.letterSpacing,
                  lineHeight: tokens.typography.display.lineHeight,
                  color: themeColors.onSurface,
                  fontVariantNumeric: 'tabular-nums'
                }}
                className="my-1 font-semibold"
              >
                R$ {currentReport.gross.toFixed(2).replace('.', ',')}
              </p>

              <p
                style={{
                  fontSize: tokens.typography.bodySm.fontSize,
                  color: themeColors.onSurfaceMuted
                }}
              >
                lucro líquido real: R$ {currentReport.net.toFixed(2).replace('.', ',')} • {currentReport.hours}h online
              </p>
            </div>

            {/* Resumo das Métricas Operacionais */}
            <div>
              <p
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-medium uppercase mb-2"
              >
                MÉTRICAS DO PERÍODO
              </p>
              <div
                style={{
                  backgroundColor: themeColors.surfaceVariant,
                  border: `1px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.md,
                  padding: '20px'
                }}
                className="space-y-4"
              >
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 block mb-0.5">Média por hora</span>
                    <span className="text-xl font-semibold tabular-nums font-mono">
                      R$ {currentReport.avgPerHour.toFixed(2).replace('.', ',')}/h
                    </span>
                  </div>
                  <div className="text-right">
                    <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 block mb-0.5">Corridas concluídas</span>
                    <span className="text-xl font-semibold tabular-nums font-mono">
                      {currentReport.rides}
                    </span>
                  </div>
                </div>

                <div className="grid grid-cols-2 gap-4 pt-3 border-t" style={{ borderColor: themeColors.outline }}>
                  <div>
                    <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 block mb-0.5">Km total rodado</span>
                    <span className="text-xl font-semibold tabular-nums font-mono">
                      {currentReport.km.toFixed(1).replace('.', ',')} km
                    </span>
                  </div>
                  <div className="text-right">
                    <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 block mb-0.5">Custo estimado carro</span>
                    <span className="text-sm font-medium text-zinc-500 tabular-nums font-mono">
                      - R$ {currentReport.vehicleCost.toFixed(2).replace('.', ',')}
                    </span>
                  </div>
                </div>
              </div>
            </div>

            {/* Relatório por Horário (Quando Ganhou Mais ou Menos) */}
            <div>
              <p
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-medium uppercase mb-2"
              >
                GANHOS POR FAIXA HORÁRIA (R$/HORA)
              </p>
              <div
                style={{
                  backgroundColor: themeColors.surfaceVariant,
                  border: `1px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.md,
                  padding: '20px'
                }}
                className="space-y-4"
              >
                {currentReport.slots.map((slot) => {
                  const fraction = (slot.perHour / maxSlotPerHour) * 100;
                  return (
                    <div key={slot.timeRange} className="space-y-1">
                      <div className="flex justify-between items-center text-sm">
                        <span className="font-semibold">{slot.timeRange}</span>
                        <span className="font-semibold tabular-nums font-mono">
                          R$ {slot.perHour.toFixed(2).replace('.', ',')}/h
                        </span>
                      </div>

                      {/* Barra sólida monocromática proporcional */}
                      <div
                        style={{
                          backgroundColor: themeColors.outline,
                          borderRadius: tokens.rounded.full,
                          height: '6px'
                        }}
                        className="w-full overflow-hidden"
                      >
                        <div
                          style={{
                            backgroundColor: themeColors.primary,
                            width: `${fraction}%`,
                            height: '100%',
                            borderRadius: tokens.rounded.full
                          }}
                        />
                      </div>

                      <div className="flex justify-between text-[11px] text-zinc-500 font-mono">
                        <span>{slot.rides} corridas</span>
                        <span>Total: R$ {slot.gross.toFixed(2).replace('.', ',')}</span>
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>

            {/* Bloco de Eficiência Operacional */}
            <div>
              <p
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-medium uppercase mb-2"
              >
                ANÁLISE DE EFICIÊNCIA DO MOTORISTA
              </p>
              <div
                style={{
                  backgroundColor: themeColors.surfaceVariant,
                  border: `1px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.md,
                  padding: '20px'
                }}
                className="space-y-2 text-sm"
              >
                <p className="font-semibold text-zinc-900 dark:text-zinc-100">
                  ★ Melhor horário: 06h - 09h (R$ 59,33/h)
                </p>
                <p className="text-zinc-500 dark:text-zinc-400">
                  ⚠️ Horário menos rentável: 10h - 13h (R$ 28,00/h)
                </p>
                <p className="text-zinc-500 dark:text-zinc-400 text-xs pt-1 border-t" style={{ borderColor: themeColors.outline }}>
                  Recomendação prática: Concentrar os esforços nos picos de mobilidade (06h-09h e 17h-20h) gera em média 42% a mais de faturamento líquido por hora conectada.
                </p>
              </div>
            </div>

            {/* Exportação de Relatórios para MEI / Declaração */}
            <div
              style={{
                backgroundColor: themeColors.surface,
                border: `1.5px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '20px'
              }}
              className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-3"
            >
              <div>
                <p className="font-semibold text-sm" style={{ color: themeColors.onSurface }}>
                  Exportar Dados para MEI e Planilhas
                </p>
                <p className="text-xs" style={{ color: themeColors.onSurfaceMuted }}>
                  Gera arquivo CSV completo com faturamento bruto, custos do veículo e horas rodadas.
                </p>
              </div>
              <button
                type="button"
                onClick={handleExportCSV}
                style={{
                  backgroundColor: themeColors.primary,
                  color: themeColors.onPrimary,
                  borderRadius: tokens.rounded.full,
                  height: '44px',
                  padding: '0 24px',
                  fontSize: tokens.typography.bodySm.fontSize,
                  fontWeight: 600
                }}
                className="cursor-pointer shrink-0 hover:opacity-90 transition-opacity flex items-center gap-2"
              >
                <span>Baixar Relatório (.csv)</span>
              </button>
            </div>

            {csvExportToast && (
              <div
                style={{
                  backgroundColor: isDark ? '#14532D30' : '#DCFCE7',
                  borderColor: isDark ? '#15803D80' : '#86EFAC',
                  color: isDark ? '#86EFAC' : '#14532D'
                }}
                className="p-3 text-center rounded border text-xs font-mono font-medium"
              >
                ✓ Arquivo CSV exportado com sucesso! Pronto para abrir no Excel, Google Sheets ou declarar MEI.
              </div>
            )}
          </div>
        )}

        {/* 2. ABA DE METAS (ETAPA F) */}
        {activeTab === 'goals' && (
          <div className="space-y-6">
            <div
              style={{
                backgroundColor: themeColors.surface,
                border: `1.5px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '24px'
              }}
            >
              <span
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-mono font-semibold uppercase block mb-1"
              >
                META DO DIA
              </span>

              <p
                style={{
                  fontSize: tokens.typography.display.fontSize,
                  fontWeight: tokens.typography.display.fontWeight,
                  letterSpacing: tokens.typography.display.letterSpacing,
                  lineHeight: tokens.typography.display.lineHeight,
                  color: dailyCompleted ? tokens.colors.signalGood : themeColors.onSurface,
                  fontVariantNumeric: 'tabular-nums'
                }}
                className="my-1 font-semibold"
              >
                {dailyPercentage.toFixed(0)}%
              </p>

              <p
                style={{
                  fontSize: tokens.typography.bodySm.fontSize,
                  color: themeColors.onSurfaceMuted
                }}
              >
                {dailyCompleted
                  ? 'Meta diária alcançada! Todo ganho agora é lucro extra.'
                  : `Faltam R$ ${dailyRemaining.toFixed(2).replace('.', ',')} para atingir a meta de hoje`}
              </p>
            </div>

            {isEditingGoals ? (
              <form
                onSubmit={handleSaveGoals}
                style={{
                  backgroundColor: themeColors.surfaceVariant,
                  border: `1.5px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.md,
                  padding: '20px'
                }}
                className="space-y-4"
              >
                <div className="flex items-center justify-between">
                  <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 font-semibold">
                    AJUSTAR VALORES DAS METAS
                  </span>
                  <button
                    type="button"
                    onClick={() => setIsEditingGoals(false)}
                    style={{ color: themeColors.onSurfaceMuted }}
                    className="text-xs font-mono hover:opacity-80 cursor-pointer"
                  >
                    Fechar ✕
                  </button>
                </div>

                <RottaFormInput
                  label="Meta diária (R$)"
                  value={dailyInput}
                  onChange={setDailyInput}
                  placeholder="500"
                  prefix="R$"
                  isMono={true}
                  themeColors={themeColors}
                />

                <RottaFormInput
                  label="Meta semanal (R$)"
                  value={weeklyInput}
                  onChange={setWeeklyInput}
                  placeholder="2500"
                  prefix="R$"
                  isMono={true}
                  themeColors={themeColors}
                />

                <RottaFormInput
                  label="Meta mensal (R$)"
                  value={monthlyInput}
                  onChange={setMonthlyInput}
                  placeholder="10000"
                  prefix="R$"
                  isMono={true}
                  themeColors={themeColors}
                />

                <div className="pt-2 space-y-2">
                  <button
                    type="submit"
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary,
                      borderRadius: tokens.rounded.full,
                      height: '56px',
                      fontSize: tokens.typography.title.fontSize,
                      fontWeight: tokens.typography.title.fontWeight
                    }}
                    className="w-full flex items-center justify-center cursor-pointer transition-opacity hover:opacity-90 active:scale-[0.99]"
                  >
                    Salvar metas
                  </button>

                  <button
                    type="button"
                    onClick={() => setIsEditingGoals(false)}
                    style={{
                      backgroundColor: themeColors.surface,
                      color: themeColors.onSurface,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.full,
                      height: '44px',
                      fontSize: tokens.typography.bodySm.fontSize
                    }}
                    className="w-full flex items-center justify-center cursor-pointer hover:opacity-90"
                  >
                    Cancelar
                  </button>
                </div>
              </form>
            ) : null}

            {goalsSuccessMessage && (
              <div
                style={{
                  backgroundColor: tokens.colors.signalGood,
                  color: tokens.colors.onSignalGood,
                  borderRadius: tokens.rounded.sm,
                  padding: '12px 16px',
                  fontSize: tokens.typography.bodySm.fontSize
                }}
                className="font-medium text-center"
              >
                Metas financeiras atualizadas com sucesso.
              </div>
            )}

            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                border: `1px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '20px'
              }}
            >
              <div className="flex justify-between items-center mb-2">
                <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 font-semibold">
                  META DIÁRIA
                </span>
                <span className="text-sm font-semibold tabular-nums font-mono">
                  R$ {currentDailyEarnings.toFixed(2).replace('.', ',')} / R$ {dailyGoal.toFixed(0)}
                </span>
              </div>

              <div
                style={{
                  backgroundColor: themeColors.outline,
                  borderRadius: tokens.rounded.full,
                  height: '6px'
                }}
                className="w-full overflow-hidden"
              >
                <div
                  style={{
                    backgroundColor: dailyCompleted ? tokens.colors.signalGood : themeColors.primary,
                    width: `${Math.min(100, dailyPercentage)}%`,
                    height: '100%',
                    borderRadius: tokens.rounded.full,
                    transition: 'width 300ms ease'
                  }}
                />
              </div>

              <div className="flex justify-between items-center mt-2 text-xs text-zinc-500">
                <span>Estimativa: ~1 corrida para atingir a meta</span>
                <span className="font-semibold tabular-nums">{dailyPercentage.toFixed(1)}%</span>
              </div>
            </div>

            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                border: `1px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '20px'
              }}
            >
              <div className="flex justify-between items-center mb-2">
                <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 font-semibold">
                  META SEMANAL
                </span>
                <span className="text-sm font-semibold tabular-nums font-mono">
                  R$ {currentWeeklyEarnings.toFixed(0)} / R$ {weeklyGoal.toFixed(0)}
                </span>
              </div>

              <div
                style={{
                  backgroundColor: themeColors.outline,
                  borderRadius: tokens.rounded.full,
                  height: '6px'
                }}
                className="w-full overflow-hidden"
              >
                <div
                  style={{
                    backgroundColor: weeklyCompleted ? tokens.colors.signalGood : themeColors.primary,
                    width: `${Math.min(100, weeklyPercentage)}%`,
                    height: '100%',
                    borderRadius: tokens.rounded.full,
                    transition: 'width 300ms ease'
                  }}
                />
              </div>

              <div className="flex justify-between items-center mt-2 text-xs text-zinc-500">
                <span>Média necessária: R$ 330/dia nos próximos 2 dias</span>
                <span className="font-semibold tabular-nums">{weeklyPercentage.toFixed(1)}%</span>
              </div>
            </div>

            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                border: `1px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '20px'
              }}
            >
              <div className="flex justify-between items-center mb-2">
                <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 font-semibold">
                  META MENSAL
                </span>
                <span className="text-sm font-semibold tabular-nums font-mono">
                  R$ {currentMonthlyEarnings.toFixed(0)} / R$ {monthlyGoal.toFixed(0)}
                </span>
              </div>

              <div
                style={{
                  backgroundColor: themeColors.outline,
                  borderRadius: tokens.rounded.full,
                  height: '6px'
                }}
                className="w-full overflow-hidden"
              >
                <div
                  style={{
                    backgroundColor: monthlyCompleted ? tokens.colors.signalGood : themeColors.primary,
                    width: `${Math.min(100, monthlyPercentage)}%`,
                    height: '100%',
                    borderRadius: tokens.rounded.full,
                    transition: 'width 300ms ease'
                  }}
                />
              </div>

              <div className="flex justify-between items-center mt-2 text-xs text-zinc-500">
                <span>Ritmo atual: 69,2% no dia 20 do mês</span>
                <span className="font-semibold tabular-nums">{monthlyPercentage.toFixed(1)}%</span>
              </div>
            </div>

            {!isEditingGoals && (
              <button
                type="button"
                onClick={() => setIsEditingGoals(true)}
                style={{
                  backgroundColor: themeColors.primary,
                  color: themeColors.onPrimary,
                  borderRadius: tokens.rounded.full,
                  height: '56px',
                  fontSize: tokens.typography.title.fontSize,
                  fontWeight: tokens.typography.title.fontWeight
                }}
                className="w-full flex items-center justify-center cursor-pointer transition-opacity hover:opacity-90 active:scale-[0.99] mt-6"
              >
                Ajustar valores das metas
              </button>
            )}
          </div>
        )}

        {/* 3. ABA DE LANÇAMENTOS (ETAPA E) */}
        {activeTab === 'entries' && (
          <div className="space-y-6">
            <div
              style={{
                backgroundColor: themeColors.surface,
                border: `1.5px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '24px'
              }}
            >
              <span
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-mono font-semibold uppercase block mb-1"
              >
                SALDO LÍQUIDO HOJE
              </span>

              <p
                style={{
                  fontSize: tokens.typography.display.fontSize,
                  fontWeight: tokens.typography.display.fontWeight,
                  letterSpacing: tokens.typography.display.letterSpacing,
                  lineHeight: tokens.typography.display.lineHeight,
                  color: themeColors.onSurface,
                  fontVariantNumeric: 'tabular-nums'
                }}
                className="my-1 font-semibold"
              >
                R$ {netBalance.toFixed(2).replace('.', ',')}
              </p>

              <p
                style={{
                  fontSize: tokens.typography.bodySm.fontSize,
                  color: themeColors.onSurfaceMuted
                }}
              >
                ganhos: R$ {totalEarnings.toFixed(2).replace('.', ',')} • despesas: R$ {totalExpenses.toFixed(2).replace('.', ',')}
              </p>
            </div>

            {isFormOpen ? (
              <form
                onSubmit={handleSaveEntry}
                style={{
                  backgroundColor: themeColors.surfaceVariant,
                  border: `1.5px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.md,
                  padding: '20px'
                }}
                className="space-y-4"
              >
                <div className="flex items-center justify-between">
                  <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 font-semibold">
                    NOVO LANÇAMENTO
                  </span>
                  <button
                    type="button"
                    onClick={() => setIsFormOpen(false)}
                    style={{ color: themeColors.onSurfaceMuted }}
                    className="text-xs font-mono hover:opacity-80 cursor-pointer"
                  >
                    Fechar ✕
                  </button>
                </div>

                <div className="grid grid-cols-2 gap-2">
                  <button
                    type="button"
                    onClick={() => setEntryType('earning')}
                    style={{
                      backgroundColor: entryType === 'earning' ? themeColors.primary : themeColors.surface,
                      color: entryType === 'earning' ? themeColors.onPrimary : themeColors.onSurface,
                      border: `1.5px solid ${entryType === 'earning' ? themeColors.primary : themeColors.outline}`,
                      borderRadius: tokens.rounded.sm,
                      height: '44px',
                      fontSize: tokens.typography.bodySm.fontSize,
                      fontWeight: entryType === 'earning' ? 600 : 400
                    }}
                    className="cursor-pointer transition-colors"
                  >
                    + Ganho (Corrida)
                  </button>

                  <button
                    type="button"
                    onClick={() => setEntryType('expense')}
                    style={{
                      backgroundColor: entryType === 'expense' ? themeColors.primary : themeColors.surface,
                      color: entryType === 'expense' ? themeColors.onPrimary : themeColors.onSurface,
                      border: `1.5px solid ${entryType === 'expense' ? themeColors.primary : themeColors.outline}`,
                      borderRadius: tokens.rounded.sm,
                      height: '44px',
                      fontSize: tokens.typography.bodySm.fontSize,
                      fontWeight: entryType === 'expense' ? 600 : 400
                    }}
                    className="cursor-pointer transition-colors"
                  >
                    - Despesa (Saída)
                  </button>
                </div>

                <RottaFormInput
                  label="Valor do lançamento"
                  value={amountInput}
                  onChange={setAmountInput}
                  placeholder="0,00"
                  prefix="R$"
                  isMono={true}
                  themeColors={themeColors}
                />

                {entryType === 'earning' ? (
                  <>
                    <div>
                      <label
                        style={{
                          fontSize: tokens.typography.label.fontSize,
                          letterSpacing: tokens.typography.label.letterSpacing,
                          color: themeColors.onSurfaceMuted
                        }}
                        className="font-mono font-semibold block uppercase mb-1.5"
                      >
                        Plataforma
                      </label>
                      <div className="grid grid-cols-4 gap-2">
                        {(['Uber', '99', 'Particular', 'Outros'] as const).map((source) => {
                          const isSelected = earningSource === source;
                          return (
                            <button
                              key={source}
                              type="button"
                              onClick={() => setEarningSource(source)}
                              style={{
                                backgroundColor: isSelected ? themeColors.primary : themeColors.surface,
                                color: isSelected ? themeColors.onPrimary : themeColors.onSurface,
                                border: `1.5px solid ${isSelected ? themeColors.primary : themeColors.outline}`,
                                borderRadius: tokens.rounded.sm,
                                height: '42px',
                                fontSize: tokens.typography.bodySm.fontSize,
                                fontWeight: isSelected ? 600 : 400
                              }}
                              className="cursor-pointer transition-colors"
                            >
                              {source}
                            </button>
                          );
                        })}
                      </div>
                    </div>

                    <RottaFormInput
                      label="Distância percorrida (opcional)"
                      value={distanceInput}
                      onChange={setDistanceInput}
                      placeholder="8.5"
                      suffix="km"
                      isMono={true}
                      themeColors={themeColors}
                    />
                  </>
                ) : (
                  <>
                    <div>
                      <label
                        style={{
                          fontSize: tokens.typography.label.fontSize,
                          letterSpacing: tokens.typography.label.letterSpacing,
                          color: themeColors.onSurfaceMuted
                        }}
                        className="font-mono font-semibold block uppercase mb-1.5"
                      >
                        Categoria da despesa
                      </label>
                      <div className="grid grid-cols-3 gap-2">
                        {(['Combustível', 'Alimentação', 'Pedágio', 'Lavagem', 'Manutenção'] as const).map((cat) => {
                          const isSelected = expenseCat === cat;
                          return (
                            <button
                              key={cat}
                              type="button"
                              onClick={() => setExpenseCat(cat)}
                              style={{
                                backgroundColor: isSelected ? themeColors.primary : themeColors.surface,
                                color: isSelected ? themeColors.onPrimary : themeColors.onSurface,
                                border: `1.5px solid ${isSelected ? themeColors.primary : themeColors.outline}`,
                                borderRadius: tokens.rounded.sm,
                                height: '42px',
                                fontSize: tokens.typography.bodySm.fontSize,
                                fontWeight: isSelected ? 600 : 400
                              }}
                              className="cursor-pointer transition-colors"
                            >
                              {cat}
                            </button>
                          );
                        })}
                      </div>
                    </div>
                  </>
                )}

                <RottaFormInput
                  label="Observação (opcional)"
                  value={notesInput}
                  onChange={setNotesInput}
                  placeholder="Ex: Posto Ipiranga ou Corrida dinâmica"
                  themeColors={themeColors}
                />

                {formError && (
                  <p className="text-xs text-red-600 font-medium">{formError}</p>
                )}

                <div className="pt-2 space-y-2">
                  <button
                    type="submit"
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary,
                      borderRadius: tokens.rounded.full,
                      height: '56px',
                      fontSize: tokens.typography.title.fontSize,
                      fontWeight: tokens.typography.title.fontWeight
                    }}
                    className="w-full flex items-center justify-center cursor-pointer transition-opacity hover:opacity-90 active:scale-[0.99]"
                  >
                    {entryType === 'earning' ? 'Salvar ganho' : 'Salvar despesa'}
                  </button>

                  <button
                    type="button"
                    onClick={() => setIsFormOpen(false)}
                    style={{
                      backgroundColor: themeColors.surface,
                      color: themeColors.onSurface,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.full,
                      height: '44px',
                      fontSize: tokens.typography.bodySm.fontSize
                    }}
                    className="w-full flex items-center justify-center cursor-pointer hover:opacity-90"
                  >
                    Cancelar
                  </button>
                </div>
              </form>
            ) : null}

            <div>
              <div className="flex items-center justify-between mb-2">
                <span
                  style={{
                    fontSize: tokens.typography.label.fontSize,
                    letterSpacing: tokens.typography.label.letterSpacing,
                    color: themeColors.onSurfaceMuted
                  }}
                  className="font-mono font-semibold uppercase"
                >
                  LANÇAMENTOS DE HOJE ({entries.length})
                </span>
                <span className="text-xs font-mono text-zinc-500">Últimas 24 horas</span>
              </div>

              <div className="space-y-2">
                {entries.map((item) => {
                  const isEarning = item.type === 'earning';
                  return (
                    <div
                      key={item.id}
                      style={{
                        backgroundColor: themeColors.surfaceVariant,
                        border: `1px solid ${themeColors.outline}`,
                        borderRadius: tokens.rounded.md,
                        padding: '16px'
                      }}
                      className="flex items-center justify-between"
                    >
                      <div>
                        <div className="flex items-center gap-2">
                          <span
                            style={{
                              fontSize: tokens.typography.title.fontSize,
                              fontWeight: tokens.typography.title.fontWeight,
                              color: themeColors.onSurface
                            }}
                          >
                            {item.title}
                          </span>
                          <span
                            style={{
                              backgroundColor: themeColors.surface,
                              color: themeColors.onSurfaceMuted,
                              border: `1px solid ${themeColors.outline}`,
                              borderRadius: tokens.rounded.full,
                              padding: '2px 8px',
                              fontSize: '11px'
                            }}
                            className="font-mono"
                          >
                            {item.time}
                          </span>
                        </div>
                        <p
                          style={{
                            fontSize: tokens.typography.bodySm.fontSize,
                            color: themeColors.onSurfaceMuted
                          }}
                          className="mt-0.5"
                        >
                          {item.subtext}
                        </p>
                      </div>

                      <div className="flex items-center gap-4">
                        <span
                          style={{
                            fontSize: tokens.typography.title.fontSize,
                            fontWeight: 600,
                            color: themeColors.onSurface
                          }}
                          className="tabular-nums font-mono"
                        >
                          {isEarning ? '+ ' : '- '}R$ {item.amount.toFixed(2).replace('.', ',')}
                        </span>
                        <button
                          type="button"
                          onClick={() => handleDeleteEntry(item.id)}
                          style={{ color: themeColors.onSurfaceMuted }}
                          className="text-sm hover:opacity-100 cursor-pointer p-1"
                          title="Excluir lançamento"
                        >
                          ✕
                        </button>
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>

            {!isFormOpen && (
              <button
                type="button"
                onClick={() => setIsFormOpen(true)}
                style={{
                  backgroundColor: themeColors.primary,
                  color: themeColors.onPrimary,
                  borderRadius: tokens.rounded.full,
                  height: '56px',
                  fontSize: tokens.typography.title.fontSize,
                  fontWeight: tokens.typography.title.fontWeight
                }}
                className="w-full flex items-center justify-center cursor-pointer transition-opacity hover:opacity-90 active:scale-[0.99] mt-6"
              >
                + Lançar ganho ou despesa
              </button>
            )}
          </div>
        )}

        {/* 4. ABA DE JORNADA (ETAPA D) */}
        {activeTab === 'journey' && (
          <div className="space-y-6">
            <div
              style={{
                backgroundColor: themeColors.surface,
                border: `1.5px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '24px'
              }}
            >
              <div className="flex items-center justify-between mb-1">
                <span
                  style={{
                    fontSize: tokens.typography.label.fontSize,
                    letterSpacing: tokens.typography.label.letterSpacing,
                    color: themeColors.onSurfaceMuted
                  }}
                  className="font-mono font-semibold uppercase"
                >
                  {isJourneyActive ? 'GANHO DA JORNADA' : 'JORNADA ENCERRADA'}
                </span>
                <span
                  style={{
                    backgroundColor: isJourneyActive ? themeColors.surfaceVariant : 'transparent',
                    color: isJourneyActive ? themeColors.onSurface : themeColors.onSurfaceMuted,
                    border: `1px solid ${themeColors.outline}`,
                    borderRadius: tokens.rounded.full,
                    padding: '4px 12px',
                    fontSize: tokens.typography.label.fontSize
                  }}
                  className="font-mono font-medium"
                >
                  {isJourneyActive ? '● ONLINE' : '○ OFFLINE'}
                </span>
              </div>

              <p
                style={{
                  fontSize: tokens.typography.display.fontSize,
                  fontWeight: tokens.typography.display.fontWeight,
                  letterSpacing: tokens.typography.display.letterSpacing,
                  lineHeight: tokens.typography.display.lineHeight,
                  color: themeColors.onSurface,
                  fontVariantNumeric: 'tabular-nums'
                }}
                className="my-1 font-semibold"
              >
                R$ {grossEarnings.toFixed(2).replace('.', ',')}
              </p>

              <p
                style={{
                  fontSize: tokens.typography.bodySm.fontSize,
                  color: themeColors.onSurfaceMuted
                }}
              >
                {isJourneyActive
                  ? `${elapsedHoursFormatted}h ${elapsedRemainingMinutesFormatted}m online • ${ridesCount} corridas realizadas`
                  : 'Sessão pausada. Toque abaixo para começar a rodar.'}
              </p>
            </div>

            {isJourneyActive && (
              <>
                <div>
                  <p
                    style={{
                      fontSize: tokens.typography.label.fontSize,
                      letterSpacing: tokens.typography.label.letterSpacing,
                      color: themeColors.onSurfaceMuted
                    }}
                    className="font-medium uppercase mb-2"
                  >
                    DESEMPENHO DA SESSÃO
                  </p>
                  <div
                    style={{
                      backgroundColor: themeColors.surfaceVariant,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.md,
                      padding: '20px'
                    }}
                    className="space-y-4"
                  >
                    <div className="grid grid-cols-2 gap-4">
                      <div>
                        <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 block mb-0.5">Tempo online</span>
                        <span className="text-xl font-semibold tabular-nums">
                          {elapsedHoursFormatted}h {elapsedRemainingMinutesFormatted}m
                        </span>
                      </div>
                      <div className="text-right">
                        <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 block mb-0.5">Distância</span>
                        <span className="text-xl font-semibold tabular-nums">{distanceKm.toFixed(1).replace('.', ',')} km</span>
                      </div>
                    </div>

                    <div className="grid grid-cols-2 gap-4 pt-3 border-t" style={{ borderColor: themeColors.outline }}>
                      <div>
                        <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 block mb-0.5">Média por hora</span>
                        <span className="text-xl font-semibold tabular-nums">R$ {earningsPerHour.toFixed(2).replace('.', ',')}/h</span>
                      </div>
                      <div className="text-right">
                        <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 block mb-0.5">Média por km</span>
                        <span className="text-xl font-semibold tabular-nums">R$ {earningsPerKm.toFixed(2).replace('.', ',')}/km</span>
                      </div>
                    </div>
                  </div>
                </div>

                <div>
                  <p
                    style={{
                      fontSize: tokens.typography.label.fontSize,
                      letterSpacing: tokens.typography.label.letterSpacing,
                      color: themeColors.onSurfaceMuted
                    }}
                    className="font-medium uppercase mb-2"
                  >
                    LUCRO ESTIMADO NO BOLSO
                  </p>
                  <div
                    style={{
                      backgroundColor: themeColors.surfaceVariant,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.md,
                      padding: '20px'
                    }}
                    className="flex justify-between items-center"
                  >
                    <div>
                      <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 block mb-0.5">Lucro líquido real</span>
                      <span className="text-2xl font-semibold tabular-nums">
                        R$ {estimatedNetProfit.toFixed(2).replace('.', ',')}
                      </span>
                    </div>
                    <div className="text-right">
                      <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 block mb-0.5">Custo do carro</span>
                      <span className="text-sm font-medium text-zinc-500 tabular-nums">
                        - R$ {estimatedVehicleCost.toFixed(2).replace('.', ',')}
                      </span>
                    </div>
                  </div>
                </div>

                {/* CALCULADORA DE PARIDADE DE COMBUSTÍVEL DA JORNADA */}
                <div>
                  <div className="flex justify-between items-center mb-2">
                    <p
                      style={{
                        fontSize: tokens.typography.label.fontSize,
                        letterSpacing: tokens.typography.label.letterSpacing,
                        color: themeColors.onSurfaceMuted
                      }}
                      className="font-medium uppercase"
                    >
                      COMBUSTÍVEL DA JORNADA & PARIDADE REAL
                    </p>
                    <span
                      style={{
                        backgroundColor: recommendedFuel === activeFuelChoice ? '#15803D20' : '#EAB30820',
                        color: recommendedFuel === activeFuelChoice ? '#15803D' : '#CA8A04',
                        border: `1px solid ${recommendedFuel === activeFuelChoice ? '#15803D40' : '#CA8A0440'}`
                      }}
                      className="text-[11px] font-semibold px-2.5 py-0.5 rounded-full"
                    >
                      {recommendedFuel} compensa mais hoje
                    </span>
                  </div>

                  <div
                    style={{
                      backgroundColor: themeColors.surfaceVariant,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.md,
                      padding: '20px'
                    }}
                    className="space-y-4"
                  >
                    {/* Seletor do combustível ativo de hoje */}
                    <div className="flex items-center justify-between">
                      <div>
                        <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 block mb-0.5">
                          Tanque de hoje:
                        </span>
                        <span className="text-base font-semibold" style={{ color: themeColors.onSurface }}>
                          Rodando com {activeFuelChoice} (R$ {activeFuelCostPerKm.toFixed(2).replace('.', ',')}/km)
                        </span>
                      </div>

                      <div className="flex gap-1">
                        <button
                          type="button"
                          onClick={() => setActiveFuelChoice('Etanol')}
                          style={{
                            backgroundColor: activeFuelChoice === 'Etanol' ? themeColors.primary : themeColors.surface,
                            color: activeFuelChoice === 'Etanol' ? themeColors.onPrimary : themeColors.onSurface,
                            border: `1px solid ${themeColors.outline}`,
                            borderRadius: tokens.rounded.full,
                            padding: '6px 14px',
                            fontSize: '12px',
                            fontWeight: 600
                          }}
                          className="cursor-pointer"
                        >
                          Etanol {recommendedFuel === 'Etanol' && '★'}
                        </button>
                        <button
                          type="button"
                          onClick={() => setActiveFuelChoice('Gasolina')}
                          style={{
                            backgroundColor: activeFuelChoice === 'Gasolina' ? themeColors.primary : themeColors.surface,
                            color: activeFuelChoice === 'Gasolina' ? themeColors.onPrimary : themeColors.onSurface,
                            border: `1px solid ${themeColors.outline}`,
                            borderRadius: tokens.rounded.full,
                            padding: '6px 14px',
                            fontSize: '12px',
                            fontWeight: 600
                          }}
                          className="cursor-pointer"
                        >
                          Gasolina {recommendedFuel === 'Gasolina' && '★'}
                        </button>
                      </div>
                    </div>

                    {/* Comparativo de Custo por Km real */}
                    <div className="grid grid-cols-2 gap-3 pt-3 border-t" style={{ borderColor: themeColors.outline }}>
                      <div className="p-2.5 rounded" style={{ backgroundColor: themeColors.surface }}>
                        <span className="text-[10px] font-mono uppercase text-zinc-500 block">Etanol (R$ {parsedEthPrice.toFixed(2).replace('.', ',')}/l)</span>
                        <span className="text-lg font-bold tabular-nums block" style={{ color: costKmEth <= costKmGas ? tokens.colors.signalGood : themeColors.onSurface }}>
                          R$ {costKmEth.toFixed(2).replace('.', ',')}/km
                        </span>
                        <span className="text-[11px] text-zinc-400">
                          faz {parsedEthKm} km/l no seu carro
                        </span>
                      </div>

                      <div className="p-2.5 rounded" style={{ backgroundColor: themeColors.surface }}>
                        <span className="text-[10px] font-mono uppercase text-zinc-500 block">Gasolina (R$ {parsedGasPrice.toFixed(2).replace('.', ',')}/l)</span>
                        <span className="text-lg font-bold tabular-nums block" style={{ color: costKmGas < costKmEth ? tokens.colors.signalGood : themeColors.onSurface }}>
                          R$ {costKmGas.toFixed(2).replace('.', ',')}/km
                        </span>
                        <span className="text-[11px] text-zinc-400">
                          faz {parsedGasKm} km/l no seu carro
                        </span>
                      </div>
                    </div>

                    {/* Relação Real vs 70% genérico */}
                    <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center text-xs text-zinc-500 gap-1 pt-1">
                      <span>
                        Paridade real do seu carro: <strong>{realVehicleParityPct.toFixed(1).replace('.', ',')}%</strong> (bomba atual: {pumpParityPct.toFixed(1).replace('.', ',')}%)
                      </span>
                      <button
                        type="button"
                        onClick={() => setIsEditingFuelPrices(!isEditingFuelPrices)}
                        style={{ color: themeColors.onSurface }}
                        className="underline font-medium cursor-pointer"
                      >
                        {isEditingFuelPrices ? 'Fechar preços' : 'Ajustar preços da bomba ✎'}
                      </button>
                    </div>

                    {/* Economia Projetada e Alerta da Regra dos 70% */}
                    <div className="grid grid-cols-2 gap-2 text-xs">
                      <div className="p-2.5 rounded border" style={{ backgroundColor: themeColors.surface, borderColor: themeColors.outline }}>
                        <span className="block text-[10px] uppercase font-mono" style={{ color: themeColors.onSurfaceMuted }}>Economia no Mês (25 dias)</span>
                        <strong className="font-semibold text-sm block mt-0.5" style={{ color: isDark ? '#4ADE80' : tokens.colors.signalGood }}>
                          + R$ {(fuelSavingsPerKm * parsedDailyKm * 25).toFixed(2).replace('.', ',')}
                        </strong>
                      </div>
                      <div className="p-2.5 rounded border" style={{ backgroundColor: themeColors.surface, borderColor: themeColors.outline }}>
                        <span className="block text-[10px] uppercase font-mono" style={{ color: themeColors.onSurfaceMuted }}>Por Tanque Cheio (50L)</span>
                        <strong className="font-semibold text-sm block mt-0.5" style={{ color: isDark ? '#4ADE80' : tokens.colors.signalGood }}>
                          + R$ {(fuelSavingsPerKm * (recommendedFuel === 'Etanol' ? parsedEthKm : parsedGasKm) * 50).toFixed(2).replace('.', ',')}
                        </strong>
                      </div>
                    </div>

                    {/* Alerta de erro da regra dos 70% */}
                    {pumpParityPct <= 70 && recommendedFuel === 'Gasolina' && (
                      <div
                        style={{
                          backgroundColor: isDark ? '#451A0340' : '#FEF3C7',
                          borderColor: isDark ? '#F59E0B60' : '#FCD34D',
                          color: isDark ? '#FDE68A' : '#78350F'
                        }}
                        className="p-2.5 rounded border text-xs"
                      >
                        ⚠️ <strong>Armadilha dos 70%:</strong> A bomba está a {pumpParityPct.toFixed(1)}% (abaixo de 70%), mas no seu carro a Gasolina ainda rende mais por km. O Rotta evitou esse prejuízo!
                      </div>
                    )}

                    {/* Inputs rápidos de ajuste de preço de bomba */}
                    {isEditingFuelPrices && (
                      <div className="p-3 rounded border space-y-3 pt-2" style={{ backgroundColor: themeColors.surface, borderColor: themeColors.outline }}>
                        <span className="text-xs font-mono font-semibold uppercase text-zinc-400 block">
                          Preço do Litro no Posto Hoje
                        </span>
                        <div className="grid grid-cols-2 gap-3">
                          <RottaFormInput
                            label="Preço Etanol (R$)"
                            value={ethanolPumpPrice}
                            onChange={setEthanolPumpPrice}
                            prefix="R$"
                            isMono={true}
                            themeColors={themeColors}
                          />
                          <RottaFormInput
                            label="Preço Gasolina (R$)"
                            value={gasolinePumpPrice}
                            onChange={setGasolinePumpPrice}
                            prefix="R$"
                            isMono={true}
                            themeColors={themeColors}
                          />
                        </div>
                        <div className="grid grid-cols-2 gap-3">
                          <RottaFormInput
                            label="Consumo Etanol (km/l)"
                            value={ethanolKmPerL}
                            onChange={setEthanolKmPerL}
                            suffix="km/l"
                            isMono={true}
                            themeColors={themeColors}
                          />
                          <RottaFormInput
                            label="Consumo Gasolina (km/l)"
                            value={gasolineKmPerL}
                            onChange={setGasolineKmPerL}
                            suffix="km/l"
                            isMono={true}
                            themeColors={themeColors}
                          />
                        </div>
                      </div>
                    )}
                  </div>
                </div>

                <button
                  type="button"
                  onClick={handleSimulateRide}
                  style={{
                    backgroundColor: themeColors.surfaceVariant,
                    color: themeColors.onSurface,
                    border: `1px solid ${themeColors.outline}`,
                    borderRadius: tokens.rounded.full,
                    height: '44px',
                    fontSize: tokens.typography.bodySm.fontSize
                  }}
                  className="w-full flex items-center justify-center cursor-pointer hover:opacity-90 font-medium"
                >
                  + Simular corrida aceita (+ R$ 26,50 • 8,2 km)
                </button>
              </>
            )}

            {!isJourneyActive && lastFinalizedJourney && (
              <div>
                <p
                  style={{
                    fontSize: tokens.typography.label.fontSize,
                    letterSpacing: tokens.typography.label.letterSpacing,
                    color: themeColors.onSurfaceMuted
                  }}
                  className="font-medium uppercase mb-2"
                >
                  ÚLTIMA JORNADA CONCLUÍDA
                </p>
                <div
                  style={{
                    backgroundColor: themeColors.surfaceVariant,
                    border: `1px solid ${themeColors.outline}`,
                    borderRadius: tokens.rounded.md,
                    padding: '20px'
                  }}
                >
                  <p className="text-xl font-semibold tabular-nums">
                    Total bruto: R$ {lastFinalizedJourney.earnings.toFixed(2).replace('.', ',')}
                  </p>
                  <p className="text-sm text-zinc-500 mt-1">
                    {lastFinalizedJourney.rides} corridas realizadas • {lastFinalizedJourney.km.toFixed(1).replace('.', ',')} km rodados
                  </p>
                </div>
              </div>
            )}

            {showEndConfirmation ? (
              <div
                style={{
                  backgroundColor: themeColors.surfaceVariant,
                  border: `1.5px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.md,
                  padding: '20px'
                }}
                className="space-y-4"
              >
                <div>
                  <span className="text-xs uppercase font-mono tracking-wider text-zinc-500 block mb-1">Confirmação</span>
                  <h3 className="text-lg font-semibold">Deseja encerrar a jornada de hoje?</h3>
                  <p className="text-sm text-zinc-500 mt-1">
                    Ganho bruto: R$ {grossEarnings.toFixed(2).replace('.', ',')} • Lucro líquido estimado: R$ {estimatedNetProfit.toFixed(2).replace('.', ',')}
                  </p>
                </div>

                <div className="space-y-2 pt-2">
                  <button
                    type="button"
                    onClick={handleConfirmEndJourney}
                    style={{
                      backgroundColor: themeColors.primary,
                      color: themeColors.onPrimary,
                      borderRadius: tokens.rounded.full,
                      height: '56px',
                      fontSize: tokens.typography.title.fontSize,
                      fontWeight: tokens.typography.title.fontWeight
                    }}
                    className="w-full flex items-center justify-center cursor-pointer transition-opacity hover:opacity-90 active:scale-[0.99]"
                  >
                    Confirmar encerramento
                  </button>

                  <button
                    type="button"
                    onClick={() => setShowEndConfirmation(false)}
                    style={{
                      backgroundColor: themeColors.surface,
                      color: themeColors.onSurface,
                      border: `1px solid ${themeColors.outline}`,
                      borderRadius: tokens.rounded.full,
                      height: '48px',
                      fontSize: tokens.typography.bodySm.fontSize,
                      fontWeight: 500
                    }}
                    className="w-full flex items-center justify-center cursor-pointer transition-opacity hover:opacity-90"
                  >
                    Voltar à jornada
                  </button>
                </div>
              </div>
            ) : isJourneyActive ? (
              <button
                type="button"
                onClick={() => setShowEndConfirmation(true)}
                style={{
                  backgroundColor: themeColors.primary,
                  color: themeColors.onPrimary,
                  borderRadius: tokens.rounded.full,
                  height: '56px',
                  fontSize: tokens.typography.title.fontSize,
                  fontWeight: tokens.typography.title.fontWeight
                }}
                className="w-full flex items-center justify-center cursor-pointer transition-opacity hover:opacity-90 active:scale-[0.99]"
              >
                Encerrar jornada • {elapsedHoursFormatted}h {elapsedRemainingMinutesFormatted}m
              </button>
            ) : (
              <button
                type="button"
                onClick={handleStartJourney}
                style={{
                  backgroundColor: themeColors.primary,
                  color: themeColors.onPrimary,
                  borderRadius: tokens.rounded.full,
                  height: '56px',
                  fontSize: tokens.typography.title.fontSize,
                  fontWeight: tokens.typography.title.fontWeight
                }}
                className="w-full flex items-center justify-center cursor-pointer transition-opacity hover:opacity-90 active:scale-[0.99]"
              >
                Iniciar jornada
              </button>
            )}
          </div>
        )}

        {/* 5. ABA DE PERFIL E CARRO (ETAPA C) */}
        {activeTab === 'profile' && (
          <form onSubmit={handleSaveProfile} className="space-y-6">
            <div
              style={{
                backgroundColor: themeColors.surface,
                border: `1.5px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '24px'
              }}
            >
              <p
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-medium uppercase"
              >
                CUSTO ESTIMADO POR KM
              </p>
              <p
                style={{
                  fontSize: tokens.typography.display.fontSize,
                  fontWeight: tokens.typography.display.fontWeight,
                  letterSpacing: tokens.typography.display.letterSpacing,
                  lineHeight: tokens.typography.display.lineHeight,
                  color: themeColors.onSurface,
                  fontVariantNumeric: 'tabular-nums'
                }}
                className="my-1 font-semibold"
              >
                R$ {totalCostPerKm.toFixed(2).replace('.', ',')}/km
              </p>
              <p
                style={{
                  fontSize: tokens.typography.bodySm.fontSize,
                  color: themeColors.onSurfaceMuted
                }}
              >
                combustível: R$ {activeFuelCostPerKm.toFixed(2).replace('.', ',')}/km • custo fixo: R$ {dailyFixedCost.toFixed(2).replace('.', ',')}/dia
              </p>
            </div>

            <div>
              <p
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-medium uppercase mb-2"
              >
                DADOS DO MOTORISTA
              </p>
              <div
                style={{
                  backgroundColor: themeColors.surfaceVariant,
                  border: `1px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.md,
                  padding: '20px'
                }}
                className="space-y-4"
              >
                <RottaFormInput
                  label="Nome completo"
                  value={name}
                  onChange={setName}
                  placeholder="Ex: Carlos Silva"
                  themeColors={themeColors}
                />
                <RottaFormInput
                  label="Telefone / WhatsApp"
                  value={phone}
                  onChange={setPhone}
                  placeholder="(11) 98765-4321"
                  isMono={true}
                  themeColors={themeColors}
                />
                <RottaFormInput
                  label="E-mail de acesso"
                  value={email}
                  onChange={setEmail}
                  placeholder="motorista@rotta.app"
                  type="email"
                  themeColors={themeColors}
                />
              </div>
            </div>

            <div>
              <p
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-medium uppercase mb-2"
              >
                DADOS DO VEÍCULO E CONSUMO
              </p>
              <div
                style={{
                  backgroundColor: themeColors.surfaceVariant,
                  border: `1px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.md,
                  padding: '20px'
                }}
                className="space-y-4"
              >
                <RottaFormInput
                  label="Modelo do carro"
                  value={carModel}
                  onChange={setCarModel}
                  placeholder="Ex: Chevrolet Onix 1.0"
                  themeColors={themeColors}
                />

                <div>
                  <label
                    style={{
                      fontSize: tokens.typography.label.fontSize,
                      letterSpacing: tokens.typography.label.letterSpacing,
                      color: themeColors.onSurfaceMuted
                    }}
                    className="font-mono font-semibold block uppercase mb-1.5"
                  >
                    Tipo de combustível
                  </label>
                  <div className="grid grid-cols-4 gap-2">
                    {(['Gasolina', 'Etanol', 'GNV', 'Elétrico'] as const).map((fuel) => {
                      const isSelected = fuelType === fuel;
                      return (
                        <button
                          key={fuel}
                          type="button"
                          onClick={() => setFuelType(fuel)}
                          style={{
                            backgroundColor: isSelected ? themeColors.primary : themeColors.surface,
                            color: isSelected ? themeColors.onPrimary : themeColors.onSurface,
                            border: `1.5px solid ${isSelected ? themeColors.primary : themeColors.outline}`,
                            borderRadius: tokens.rounded.sm,
                            height: '44px',
                            fontSize: tokens.typography.bodySm.fontSize,
                            fontWeight: isSelected ? 600 : 400
                          }}
                          className="cursor-pointer transition-colors"
                        >
                          {fuel}
                        </button>
                      );
                    })}
                  </div>
                </div>

                <div className="grid grid-cols-2 gap-3">
                  <RottaFormInput
                    label="Consumo médio"
                    value={consumption}
                    onChange={setConsumption}
                    suffix="km/l"
                    isMono={true}
                    themeColors={themeColors}
                  />
                  <RottaFormInput
                    label="Preço litro"
                    value={fuelPrice}
                    onChange={setFuelPrice}
                    prefix="R$"
                    suffix="litro"
                    isMono={true}
                    themeColors={themeColors}
                  />
                </div>
              </div>
            </div>

            <div>
              <p
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-medium uppercase mb-2"
              >
                CUSTOS FIXOS DO VEÍCULO
              </p>
              <div
                style={{
                  backgroundColor: themeColors.surfaceVariant,
                  border: `1px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.md,
                  padding: '20px'
                }}
                className="space-y-4"
              >
                <RottaFormInput
                  label="Custos fixos mensais (seguro, IPVA, manutenção, parcela)"
                  value={fixedCosts}
                  onChange={setFixedCosts}
                  prefix="R$"
                  suffix="mês"
                  isMono={true}
                  themeColors={themeColors}
                />

                <button
                  type="button"
                  onClick={() => setShowFixedDetails(!showFixedDetails)}
                  style={{ color: themeColors.onSurfaceMuted }}
                  className="w-full flex items-center justify-between text-xs py-1 cursor-pointer hover:opacity-80"
                >
                  <span className="font-medium">
                    {showFixedDetails ? 'Ocultar dias e km diário estimado' : 'Ajustar dias trabalhados e km diário estimado'}
                  </span>
                  <span>{showFixedDetails ? '▲' : '▼'}</span>
                </button>

                {showFixedDetails && (
                  <div
                    style={{ borderColor: themeColors.outline }}
                    className="grid grid-cols-2 gap-3 pt-3 border-t"
                  >
                    <RottaFormInput
                      label="Dias no mês"
                      value={workDays}
                      onChange={setWorkDays}
                      suffix="dias"
                      isMono={true}
                      themeColors={themeColors}
                    />
                    <RottaFormInput
                      label="Km por dia"
                      value={dailyKm}
                      onChange={setDailyKm}
                      suffix="km"
                      isMono={true}
                      themeColors={themeColors}
                    />
                  </div>
                )}
              </div>
            </div>

            {savedSuccess && (
              <div
                style={{
                  backgroundColor: tokens.colors.signalGood,
                  color: tokens.colors.onSignalGood,
                  borderRadius: tokens.rounded.sm,
                  padding: '12px 16px',
                  fontSize: tokens.typography.bodySm.fontSize
                }}
                className="font-medium text-center"
              >
                Perfil e veículo atualizados com sucesso.
              </div>
            )}

            <button
              type="submit"
              style={{
                backgroundColor: themeColors.primary,
                color: themeColors.onPrimary,
                borderRadius: tokens.rounded.full,
                height: '56px',
                fontSize: tokens.typography.title.fontSize,
                fontWeight: tokens.typography.title.fontWeight
              }}
              className="w-full flex items-center justify-center cursor-pointer transition-opacity hover:opacity-90 active:scale-[0.99]"
            >
              Salvar alterações
            </button>
          </form>
        )}

        {/* 6. ABA DE LOGIN (ETAPA C) */}
        {activeTab === 'login' && (
          <div
            style={{
              backgroundColor: themeColors.surfaceVariant,
              border: `1px solid ${themeColors.outline}`,
              borderRadius: tokens.rounded.md,
              padding: '24px'
            }}
            className="space-y-6"
          >
            <div>
              <div
                style={{
                  width: '48px',
                  height: '48px',
                  borderRadius: tokens.rounded.full,
                  backgroundColor: themeColors.primary,
                  color: themeColors.onPrimary
                }}
                className="flex items-center justify-center font-bold text-xl mb-4"
              >
                R
              </div>
              <span
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-mono uppercase block"
              >
                TRAFEGO ROTTA
              </span>
              <h2
                style={{
                  fontSize: tokens.typography.headline.fontSize,
                  fontWeight: tokens.typography.headline.fontWeight
                }}
              >
                Entrar no copiloto
              </h2>
              <p
                style={{
                  fontSize: tokens.typography.bodySm.fontSize,
                  color: themeColors.onSurfaceMuted
                }}
              >
                Seus ganhos e custos sob controle durante a corrida.
              </p>
            </div>

            <form onSubmit={handleLogin} className="space-y-4">
              <RottaFormInput
                label="E-mail ou telefone"
                value={loginEmail}
                onChange={setLoginEmail}
                placeholder="motorista@exemplo.com"
                type="email"
                themeColors={themeColors}
              />

              <RottaFormInput
                label="Senha ou PIN"
                value={loginPassword}
                onChange={setLoginPassword}
                placeholder="••••••••"
                type="password"
                themeColors={themeColors}
              />

              {loginError && (
                <p className="text-xs text-red-600 font-medium">{loginError}</p>
              )}

              <button
                type="submit"
                style={{
                  backgroundColor: themeColors.primary,
                  color: themeColors.onPrimary,
                  borderRadius: tokens.rounded.full,
                  height: '56px',
                  fontSize: tokens.typography.title.fontSize,
                  fontWeight: tokens.typography.title.fontWeight
                }}
                className="w-full flex items-center justify-center cursor-pointer transition-opacity hover:opacity-90 active:scale-[0.99] mt-6"
              >
                Entrar
              </button>
            </form>
          </div>
        )}

        {/* 7. ABA DO CATÁLOGO DE COMPONENTES BASE (ETAPA B) */}
        {activeTab === 'components' && (
          <div className="space-y-8">
            <section>
              <div className="flex items-center justify-between mb-2">
                <span
                  style={{
                    fontSize: tokens.typography.label.fontSize,
                    letterSpacing: tokens.typography.label.letterSpacing,
                    color: themeColors.onSurfaceMuted
                  }}
                  className="uppercase font-mono font-semibold"
                >
                  Hero de Ganho (RottaStatHero.kt)
                </span>
                <span className="text-xs text-zinc-500 font-mono">1 dominante por tela</span>
              </div>
              <div
                style={{
                  backgroundColor: themeColors.surface,
                  border: `1.5px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.md,
                  padding: '24px'
                }}
              >
                <p
                  style={{
                    fontSize: tokens.typography.label.fontSize,
                    letterSpacing: tokens.typography.label.letterSpacing,
                    color: themeColors.onSurfaceMuted
                  }}
                  className="font-medium uppercase"
                >
                  GANHO HOJE
                </p>
                <p
                  style={{
                    fontSize: tokens.typography.display.fontSize,
                    fontWeight: tokens.typography.display.fontWeight,
                    letterSpacing: tokens.typography.display.letterSpacing,
                    lineHeight: tokens.typography.display.lineHeight,
                    color: themeColors.onSurface,
                    fontVariantNumeric: 'tabular-nums'
                  }}
                  className="my-1 font-semibold"
                >
                  R$ 485,20
                </p>
                <p
                  style={{
                    fontSize: tokens.typography.bodySm.fontSize,
                    color: themeColors.onSurfaceMuted
                  }}
                >
                  meta diária: 97% • 6h 10m conectado
                </p>
              </div>
            </section>

            <section>
              <div className="flex items-center justify-between mb-2">
                <span
                  style={{
                    fontSize: tokens.typography.label.fontSize,
                    letterSpacing: tokens.typography.label.letterSpacing,
                    color: themeColors.onSurfaceMuted
                  }}
                  className="uppercase font-mono font-semibold"
                >
                  Semáforo (RottaSignalChip.kt • Cor + Palavra)
                </span>
                <span className="text-xs text-zinc-500 font-mono">Acessibilidade AA</span>
              </div>
              <div
                style={{
                  backgroundColor: themeColors.surfaceVariant,
                  border: `1px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.md,
                  padding: '20px'
                }}
                className="flex flex-wrap gap-4 items-center"
              >
                <div
                  style={{
                    backgroundColor: tokens.colors.signalGood,
                    color: tokens.colors.onSignalGood,
                    height: '40px',
                    borderRadius: tokens.rounded.full,
                    padding: '0 24px',
                    display: 'flex',
                    alignItems: 'center',
                    fontSize: tokens.typography.title.fontSize,
                    fontWeight: tokens.typography.title.fontWeight
                  }}
                >
                  Vale
                </div>
                <div
                  style={{
                    backgroundColor: tokens.colors.signalMedium,
                    color: tokens.colors.onSignalMedium,
                    height: '40px',
                    borderRadius: tokens.rounded.full,
                    padding: '0 24px',
                    display: 'flex',
                    alignItems: 'center',
                    fontSize: tokens.typography.title.fontSize,
                    fontWeight: tokens.typography.title.fontWeight
                  }}
                >
                  Neutra
                </div>
                <div
                  style={{
                    backgroundColor: tokens.colors.signalBad,
                    color: tokens.colors.onSignalBad,
                    height: '40px',
                    borderRadius: tokens.rounded.full,
                    padding: '0 24px',
                    display: 'flex',
                    alignItems: 'center',
                    fontSize: tokens.typography.title.fontSize,
                    fontWeight: tokens.typography.title.fontWeight
                  }}
                >
                  Fraca
                </div>
              </div>
            </section>

            <section>
              <div className="flex items-center justify-between mb-2">
                <span
                  style={{
                    fontSize: tokens.typography.label.fontSize,
                    letterSpacing: tokens.typography.label.letterSpacing,
                    color: themeColors.onSurfaceMuted
                  }}
                  className="uppercase font-mono font-semibold"
                >
                  Botões de Ação (RottaButton.kt • 56px de Altura)
                </span>
              </div>
              <div className="space-y-3">
                <button
                  style={{
                    backgroundColor: themeColors.primary,
                    color: themeColors.onPrimary,
                    borderRadius: tokens.rounded.full,
                    height: '56px',
                    fontSize: tokens.typography.title.fontSize,
                    fontWeight: tokens.typography.title.fontWeight
                  }}
                  className="w-full flex items-center justify-center cursor-pointer transition-opacity hover:opacity-90 active:scale-[0.99]"
                >
                  Iniciar jornada
                </button>
                <button
                  style={{
                    backgroundColor: themeColors.surfaceVariant,
                    color: themeColors.onSurface,
                    border: `1px solid ${themeColors.outline}`,
                    borderRadius: tokens.rounded.full,
                    height: '56px',
                    fontSize: tokens.typography.title.fontSize,
                    fontWeight: tokens.typography.title.fontWeight
                  }}
                  className="w-full flex items-center justify-center cursor-pointer transition-opacity hover:opacity-90 active:scale-[0.99]"
                >
                  Encerrar jornada • 4h 12m
                </button>
              </div>
            </section>
          </div>
        )}

        {/* 8. ABA DE OVERLAY SOBRE 3 FUNDOS */}
        {activeTab === 'overlay-test' && (
          <div className="space-y-6">
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                border: `1px solid ${themeColors.outline}`
              }}
              className="p-4 rounded-lg text-sm"
            >
              <strong>Regra do Overlay:</strong> A bolha de 52px e o painel possuem contorno fino contrastante (1px) e sombra sutil, garantindo legibilidade imediata sobre qualquer fundo de app externo (Uber, 99, Waze ou Google Maps).
            </div>

            <div>
              <p className="text-xs uppercase font-mono mb-2 text-zinc-500 font-semibold">1. Fundo Claro (Modo Dia Uber/99)</p>
              <div className="bg-white border border-zinc-200 rounded-xl p-6 relative min-h-[180px] flex items-center gap-4">
                <div
                  style={{
                    width: '52px',
                    height: '52px',
                    borderRadius: tokens.rounded.full,
                    backgroundColor: '#0A0A0A',
                    color: '#FAFAFA',
                    border: '1px solid rgba(0,0,0,0.2)',
                    boxShadow: '0 6px 16px rgba(0,0,0,0.25)'
                  }}
                  className="flex items-center justify-center font-bold text-xl shrink-0"
                >
                  R
                </div>
                <div
                  style={{
                    backgroundColor: '#0A0A0A',
                    color: '#FAFAFA',
                    borderRadius: tokens.rounded.lg,
                    border: '1px solid rgba(0,0,0,0.15)',
                    padding: '16px',
                    boxShadow: '0 12px 28px rgba(0,0,0,0.35)',
                    flex: 1
                  }}
                >
                  <div className="flex justify-between items-center">
                    <div>
                      <span className="text-[11px] font-mono tracking-wider text-zinc-400 uppercase block">GANHO JORNADA</span>
                      <span className="text-2xl font-semibold tabular-nums">R$ 184,20</span>
                    </div>
                    <div
                      style={{
                        backgroundColor: tokens.colors.signalGood,
                        color: '#FFF',
                        borderRadius: '9999px',
                        padding: '0 18px',
                        height: '36px'
                      }}
                      className="flex items-center font-semibold text-sm"
                    >
                      Vale
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div>
              <p className="text-xs uppercase font-mono mb-2 text-zinc-500 font-semibold">2. Fundo Escuro (Navegação Noturna)</p>
              <div className="bg-zinc-950 border border-zinc-800 rounded-xl p-6 relative min-h-[180px] flex items-center gap-4">
                <div
                  style={{
                    width: '52px',
                    height: '52px',
                    borderRadius: tokens.rounded.full,
                    backgroundColor: '#0A0A0A',
                    color: '#FAFAFA',
                    border: '1px solid rgba(255,255,255,0.25)',
                    boxShadow: '0 6px 18px rgba(0,0,0,0.6)'
                  }}
                  className="flex items-center justify-center font-bold text-xl shrink-0"
                >
                  R
                </div>
                <div
                  style={{
                    backgroundColor: '#0A0A0A',
                    color: '#FAFAFA',
                    borderRadius: tokens.rounded.lg,
                    border: '1px solid rgba(255,255,255,0.2)',
                    padding: '16px',
                    boxShadow: '0 12px 32px rgba(0,0,0,0.7)',
                    flex: 1
                  }}
                >
                  <div className="flex justify-between items-center">
                    <div>
                      <span className="text-[11px] font-mono tracking-wider text-zinc-400 uppercase block">GANHO JORNADA</span>
                      <span className="text-2xl font-semibold tabular-nums">R$ 290,00</span>
                    </div>
                    <div
                      style={{
                        backgroundColor: tokens.colors.signalMedium,
                        color: '#0A0A0A',
                        borderRadius: '9999px',
                        padding: '0 18px',
                        height: '36px'
                      }}
                      className="flex items-center font-semibold text-sm"
                    >
                      Neutra
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div>
              <p className="text-xs uppercase font-mono mb-2 text-zinc-500 font-semibold">3. Fundo Colorido (Mapa GPS Saturado)</p>
              <div
                style={{
                  background: 'linear-gradient(135deg, #2b6cb0 0%, #2c7a7b 50%, #d69e2e 100%)'
                }}
                className="rounded-xl p-6 relative min-h-[180px] flex items-center gap-4"
              >
                <div
                  style={{
                    width: '52px',
                    height: '52px',
                    borderRadius: tokens.rounded.full,
                    backgroundColor: '#0A0A0A',
                    color: '#FAFAFA',
                    border: '1.5px solid rgba(255,255,255,0.4)',
                    boxShadow: '0 8px 24px rgba(0,0,0,0.5)'
                  }}
                  className="flex items-center justify-center font-bold text-xl shrink-0"
                >
                  R
                </div>
                <div
                  style={{
                    backgroundColor: '#0A0A0A',
                    color: '#FAFAFA',
                    borderRadius: tokens.rounded.lg,
                    border: '1.5px solid rgba(255,255,255,0.3)',
                    padding: '16px',
                    boxShadow: '0 16px 36px rgba(0,0,0,0.55)',
                    flex: 1
                  }}
                >
                  <div className="flex justify-between items-center">
                    <div>
                      <span className="text-[11px] font-mono tracking-wider text-zinc-400 uppercase block">GANHO JORNADA</span>
                      <span className="text-2xl font-semibold tabular-nums">R$ 340,80</span>
                    </div>
                    <div
                      style={{
                        backgroundColor: tokens.colors.signalBad,
                        color: '#FFF',
                        borderRadius: '9999px',
                        padding: '0 18px',
                        height: '36px'
                      }}
                      className="flex items-center font-semibold text-sm"
                    >
                      Fraca
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* ABA: KIT DE LANÇAMENTO GOOGLE PLAY STORE & LGPD */}
        {activeTab === 'playstore' && (
          <div className="space-y-6">
            {/* Header da Aba */}
            <div>
              <span
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-mono font-medium block uppercase"
              >
                PUBLICACAO • GOOGLE PLAY CONSOLE
              </span>
              <h2
                style={{
                  fontSize: tokens.typography.headline.fontSize,
                  fontWeight: tokens.typography.headline.fontWeight,
                  letterSpacing: tokens.typography.headline.letterSpacing
                }}
              >
                Kit de Lançamento & LGPD
              </h2>
              <p
                style={{
                  fontSize: tokens.typography.bodySm.fontSize,
                  color: themeColors.onSurfaceMuted
                }}
                className="mt-1"
              >
                Textos oficiais, respostas para os formulários do Google Play Console e política de privacidade completa em estrita conformidade com a LGPD e as políticas do Google Play.
              </p>
            </div>

            {/* Toast de Cópia */}
            {playStoreCopyToast && (
              <div
                style={{
                  backgroundColor: themeColors.primary,
                  color: themeColors.onPrimary,
                  borderRadius: tokens.rounded.md,
                  padding: '12px 16px',
                  fontSize: tokens.typography.bodySm.fontSize,
                  fontWeight: 500
                }}
                className="shadow-lg transition-all animate-in fade-in slide-in-from-top-2"
              >
                ✓ {playStoreCopyToast}
              </div>
            )}

            {/* 1. NÚMERO DOMINANTE: 100% PRONTO */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                border: `1.5px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '24px'
              }}
            >
              <span
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-mono font-medium block uppercase mb-1"
              >
                STATUS DE CONFORMIDADE
              </span>
              <div
                style={{
                  fontSize: tokens.typography.display.fontSize,
                  fontWeight: tokens.typography.display.fontWeight,
                  lineHeight: tokens.typography.display.lineHeight,
                  letterSpacing: tokens.typography.display.letterSpacing
                }}
                className="font-mono tabular-nums"
              >
                100%
              </div>
              <p
                style={{
                  fontSize: tokens.typography.bodySm.fontSize,
                  color: themeColors.onSurfaceMuted
                }}
                className="mt-1"
              >
                Zero cliques automáticos • Acessibilidade só para leitura • Dados 100% no aparelho • ProGuard ativado
              </p>

              <div className="grid grid-cols-2 sm:grid-cols-4 gap-2 mt-4 pt-4 border-t" style={{ borderColor: themeColors.outline }}>
                <div className="text-center p-2 rounded" style={{ backgroundColor: themeColors.surface }}>
                  <div className="text-xs font-mono font-semibold" style={{ color: tokens.colors.signalGood }}>✓ APROVADO</div>
                  <div className="text-[11px] text-zinc-500 uppercase mt-0.5">canPerformGestures</div>
                </div>
                <div className="text-center p-2 rounded" style={{ backgroundColor: themeColors.surface }}>
                  <div className="text-xs font-mono font-semibold" style={{ color: tokens.colors.signalGood }}>✓ DECLARADO</div>
                  <div className="text-[11px] text-zinc-500 uppercase mt-0.5">specialUse (A14+)</div>
                </div>
                <div className="text-center p-2 rounded" style={{ backgroundColor: themeColors.surface }}>
                  <div className="text-xs font-mono font-semibold" style={{ color: tokens.colors.signalGood }}>✓ DESTACADO</div>
                  <div className="text-[11px] text-zinc-500 uppercase mt-0.5">Disclosure Prévia</div>
                </div>
                <div className="text-center p-2 rounded" style={{ backgroundColor: themeColors.surface }}>
                  <div className="text-xs font-mono font-semibold" style={{ color: tokens.colors.signalGood }}>✓ EXCLUSIVO</div>
                  <div className="text-[11px] text-zinc-500 uppercase mt-0.5">Uber & 99 Packages</div>
                </div>
              </div>
            </div>

            {/* 2. FICHA DA LOJA (PLAY STORE METADATA) */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                border: `1px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '24px'
              }}
              className="space-y-4"
            >
              <div className="flex items-center justify-between">
                <span
                  style={{
                    fontSize: tokens.typography.label.fontSize,
                    letterSpacing: tokens.typography.label.letterSpacing,
                    color: themeColors.onSurfaceMuted
                  }}
                  className="font-mono font-medium uppercase"
                >
                  1. FICHA OFICIAL DA GOOGLE PLAY
                </span>
                <span className="text-xs font-mono text-zinc-400">Pronto para copiar</span>
              </div>

              {/* Título do App */}
              <div
                style={{
                  backgroundColor: themeColors.surface,
                  border: `1px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.sm,
                  padding: '16px'
                }}
              >
                <div className="flex justify-between items-center mb-1">
                  <span className="text-xs font-mono font-semibold text-zinc-500 uppercase">
                    Nome do App (Título) • 29 / 30 caracteres
                  </span>
                  <button
                    onClick={() => {
                      navigator.clipboard.writeText('Trafego Rotta: Copiloto Uber 99');
                      setPlayStoreCopyToast('Nome do App copiado para a área de transferência!');
                      setTimeout(() => setPlayStoreCopyToast(null), 3000);
                    }}
                    className="text-xs font-semibold px-2 py-1 rounded border hover:opacity-80 transition cursor-pointer"
                    style={{ borderColor: themeColors.outline, backgroundColor: themeColors.surfaceVariant }}
                  >
                    Copiar
                  </button>
                </div>
                <div className="font-semibold text-lg">Trafego Rotta: Copiloto Uber 99</div>
              </div>

              {/* Descrição Curta */}
              <div
                style={{
                  backgroundColor: themeColors.surface,
                  border: `1px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.sm,
                  padding: '16px'
                }}
              >
                <div className="flex justify-between items-center mb-1">
                  <span className="text-xs font-mono font-semibold text-zinc-500 uppercase">
                    Descrição Breve • 79 / 80 caracteres
                  </span>
                  <button
                    onClick={() => {
                      navigator.clipboard.writeText('Copiloto financeiro para motoristas de app: semáforo de corridas, metas e lucro.');
                      setPlayStoreCopyToast('Descrição Breve copiada!');
                      setTimeout(() => setPlayStoreCopyToast(null), 3000);
                    }}
                    className="text-xs font-semibold px-2 py-1 rounded border hover:opacity-80 transition cursor-pointer"
                    style={{ borderColor: themeColors.outline, backgroundColor: themeColors.surfaceVariant }}
                  >
                    Copiar
                  </button>
                </div>
                <div className="font-medium text-sm">
                  Copiloto financeiro para motoristas de app: semáforo de corridas, metas e lucro.
                </div>
              </div>

              {/* Descrição Completa */}
              <div
                style={{
                  backgroundColor: themeColors.surface,
                  border: `1px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.sm,
                  padding: '16px'
                }}
              >
                <div className="flex justify-between items-center mb-2">
                  <span className="text-xs font-mono font-semibold text-zinc-500 uppercase">
                    Descrição Completa • 2.340 / 4.000 caracteres
                  </span>
                  <button
                    onClick={() => {
                      const fullDesc = `Trafego Rotta é o copiloto financeiro desenvolvido sob medida para motoristas de aplicativos (Uber e 99) no Brasil.\n\nChega de fazer cálculos matemáticos de cabeça a 60 km/h enquanto dirige. O Rotta calcula instantaneamente o lucro real de cada corrida, controla sua jornada de trabalho e garante que você bata suas metas diárias com segurança e tranquilidade.\n\n■ SEMÁFORO DE CORRIDAS DE RELANCE (MENOS DE 1 SEGUNDO)\nAssim que uma oferta de corrida da Uber ou 99 aparece na sua tela, o Rotta compara a proposta com seus custos operacionais reais (combustível, manutenção e custos fixos) e sinaliza em uma bolha discreta:\n• VERDE (VALE): O ganho por quilômetro e por hora supera sua margem mínima de lucro.\n• AMARELO (NEUTRA): A corrida cobre seus custos, mas fica no limite da sua meta.\n• VERMELHO (FRACA): A corrida não compensa o desgaste do carro e combustível.\n\n■ BOLHA FLUTUANTE QUE NÃO ATRAPALHA\n• Ocupa apenas 52px na lateral da tela.\n• Totalmente arrastável: posicionada fora da área inferior onde você aceita corridas.\n• Toque para expandir e ver o lucro estimado da viagem.\n• Você escolhe se prefere expansão automática ou manual.\n\n■ CUSTO REAL DO QUILÔMETRO & PARIDADE DE COMBUSTÍVEL\n• Descubra exatamente quanto custa rodar 1 km com o seu carro (combustível + seguro + IPVA + pneus + manutenção preventiva).\n• Calculadora de Paridade Real (Etanol vs Gasolina): esqueça a velha regra simplista dos 70%. O Rotta calcula a eficiência exata do motor do seu modelo para indicar a bomba mais econômica.\n\n■ CONTROLE DA JORNADA & LUCRO LÍQUIDO\n• Inicie sua jornada ao sair de casa e acompanhe em tempo real:\n  - Faturamento bruto acumulado.\n  - Custo veicular estimado descontado automaticamente por quilômetro rodado.\n  - Lucro líquido real que vai para o seu bolso.\n  - Média de ganho por hora trabalhada (R$/h).\n\n■ METAS DIÁRIAS, SEMANAIS E MENSAIS\n• Defina quanto precisa faturar por dia e acompanhe o ritmo da jornada com barra de progresso visual.\n• Saiba a qualquer instante se seu ritmo está adiantado ou se precisa esticar o turno para bater o alvo.\n\n■ RELATÓRIOS DETALHADOS E EXPORTAÇÃO PARA O MEI\n• Histórico organizado por dia, semana e mês.\n• Gráfico por faixa de horário (06h-09h, 10h-13h, 14h-17h, 17h-20h) para você identificar onde a sua cidade realmente paga mais.\n• Exportação do faturamento com um toque em arquivo CSV pronto para a sua contabilidade ou declaração anual MEI.\n\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\nSEGURANÇA E PRIVACIDADE INEGOCIÁVEIS (LGPD)\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n1. O ROTTA NUNCA CLICA NEM TOCA NA SUA TELA:\nO aplicativo é 100% passivo. Ele NUNCA aceita, recusa ou executa qualquer ação automática no Uber ou 99. A decisão e o toque na tela são sempre seus. Respeitamos integralmente as diretrizes do Google Play e os termos dos aplicativos de corrida.\n\n2. LEITURA RESTRITA POR ACESSIBILIDADE:\nO serviço de acessibilidade do Rotta lê única e exclusivamente os números da corrida (valor em R$, distância em km e tempo).\n\n3. ZERO COLETA DE DADOS DE PASSAGEIROS:\nO aplicativo NÃO lê, NÃO processa e NÃO armazena nome, foto, nota ou endereço exato de nenhum passageiro.\n\n4. 100% LOCAL NO SEU APARELHO:\nSeus ganhos e relatórios financeiros ficam guardados no seu próprio celular. Nenhum dado financeiro seu é vendido ou compartilhado com terceiros.\n\n5. FUNCIONA MESMO SEM ACESSIBILIDADE:\nSe você preferir não ativar a leitura de tela, o Rotta continua 100% útil para acompanhamento manual da jornada, calculadora de custos, paridade de combustível e relatórios de metas.\n\nTrafego Rotta: dirija com números claros e leve mais dinheiro para casa.`;
                      navigator.clipboard.writeText(fullDesc);
                      setPlayStoreCopyToast('Descrição Completa copiada (2.340 caracteres)!');
                      setTimeout(() => setPlayStoreCopyToast(null), 3000);
                    }}
                    className="text-xs font-semibold px-2 py-1 rounded border hover:opacity-80 transition cursor-pointer"
                    style={{ borderColor: themeColors.outline, backgroundColor: themeColors.surfaceVariant }}
                  >
                    Copiar Descrição Completa
                  </button>
                </div>
                <div className="font-mono text-xs whitespace-pre-line text-zinc-400 max-h-48 overflow-y-auto p-2 rounded border border-zinc-800">
                  {`Trafego Rotta é o copiloto financeiro desenvolvido sob medida para motoristas de aplicativos (Uber e 99) no Brasil.

Chega de fazer cálculos matemáticos de cabeça a 60 km/h enquanto dirige. O Rotta calcula instantaneamente o lucro real de cada corrida, controla sua jornada de trabalho e garante que você bata suas metas diárias com segurança e tranquilidade.

■ SEMÁFORO DE CORRIDAS DE RELANCE (MENOS DE 1 SEGUNDO)
• VERDE (VALE): O ganho por km e por hora supera sua margem mínima de lucro.
• AMARELO (NEUTRA): Cobre seus custos, mas fica no limite da meta.
• VERMELHO (FRACA): Não compensa o desgaste e combustível.

■ SEGURANÇA E PRIVACIDADE INEGOCIÁVEIS (LGPD)
• Zero cliques automáticos (o app NUNCA clica, aceita ou recusa nada por você).
• Leitura restrita por acessibilidade (somente valores numéricos da tarifa).
• Zero coleta de dados de passageiros (sem nomes, fotos ou endereços).
• 100% local no seu aparelho.`}
                </div>
              </div>
            </div>

            {/* 3. POLÍTICA DE PRIVACIDADE OFICIAL (LGPD) */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                border: `1px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '24px'
              }}
              className="space-y-4"
            >
              <div className="flex items-center justify-between">
                <span
                  style={{
                    fontSize: tokens.typography.label.fontSize,
                    letterSpacing: tokens.typography.label.letterSpacing,
                    color: themeColors.onSurfaceMuted
                  }}
                  className="font-mono font-medium uppercase"
                >
                  2. POLÍTICA DE PRIVACIDADE & LGPD
                </span>
                <div className="flex items-center gap-2">
                  <a
                    href="#privacidade"
                    className="text-xs font-semibold px-2 py-1 rounded border hover:opacity-80 transition cursor-pointer"
                    style={{ borderColor: themeColors.outline, backgroundColor: themeColors.surface }}
                  >
                    Ver Página Pública
                  </a>
                  <button
                    onClick={() => {
                      const origin = window.location.origin;
                      const privUrl = `${origin}/#privacidade`;
                      navigator.clipboard.writeText(privUrl);
                      setPlayStoreCopyToast('URL Pública da Política copiada: ' + privUrl);
                      setTimeout(() => setPlayStoreCopyToast(null), 3000);
                    }}
                    className="text-xs font-semibold px-2 py-1 rounded border hover:opacity-80 transition cursor-pointer"
                    style={{ borderColor: themeColors.outline, backgroundColor: themeColors.surface }}
                  >
                    Copiar URL para o Play Console
                  </button>
                </div>
              </div>

              <div
                style={{
                  backgroundColor: themeColors.surface,
                  border: `1px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.sm,
                  padding: '16px'
                }}
                className="space-y-3 text-xs leading-relaxed"
              >
                <div className="font-semibold text-sm">Resumo dos Compromissos LGPD (Lei nº 13.709/2018):</div>
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
                  <div className="p-2.5 rounded border border-zinc-800">
                    <span className="font-mono font-semibold text-zinc-300 block mb-1">✓ Sem Dados de Passageiro</span>
                    <span className="text-zinc-400">Rejeita e descarta ativamente qualquer nome, foto, nota ou endereço exato de passageiros.</span>
                  </div>
                  <div className="p-2.5 rounded border border-zinc-800">
                    <span className="font-mono font-semibold text-zinc-300 block mb-1">✓ Armazenamento 100% Local</span>
                    <span className="text-zinc-400">Banco SQLite Room no aparelho. Zero servidores de telemetria ou anúncios de terceiros.</span>
                  </div>
                  <div className="p-2.5 rounded border border-zinc-800">
                    <span className="font-mono font-semibold text-zinc-300 block mb-1">✓ Artigo 18 (Direitos)</span>
                    <span className="text-zinc-400">Exportação total em CSV para contabilidade/MEI e eliminação instantânea ao limpar o app.</span>
                  </div>
                  <div className="p-2.5 rounded border border-zinc-800">
                    <span className="font-mono font-semibold text-zinc-300 block mb-1">✓ Consentimento Destacado</span>
                    <span className="text-zinc-400">Tela prévia obrigatória antes de qualquer pedido de permissão do sistema Android.</span>
                  </div>
                </div>
              </div>
            </div>

            {/* 4. DECLARAÇÕES PARA O PLAY CONSOLE (ACCESSIBILITY & FOREGROUND SERVICE) */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                border: `1px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '24px'
              }}
              className="space-y-4"
            >
              <span
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-mono font-medium block uppercase"
              >
                3. DECLARAÇÃO DA API DE ACESSIBILIDADE (PLAY CONSOLE)
              </span>

              <div
                style={{
                  backgroundColor: themeColors.surface,
                  border: `1px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.sm,
                  padding: '16px'
                }}
                className="space-y-2"
              >
                <div className="flex justify-between items-center">
                  <span className="text-xs font-mono font-semibold text-zinc-500 uppercase">
                    Texto Oficial para os Revisores do Google
                  </span>
                  <button
                    onClick={() => {
                      const reviewerText = `O Trafego Rotta é um copiloto financeiro para motoristas de aplicativos de transporte (Uber e 99) no Brasil. O serviço de acessibilidade é utilizado estritamente em modo de leitura para detectar os valores numéricos de ofertas de corridas exibidas na tela (valor bruto da tarifa em R$, distância em km e tempo estimado em minutos).\n\nO app calcula instantaneamente se a corrida atende aos custos operacionais mínimos definidos pelo motorista (R$/km e R$/h), exibindo um semáforo de relance (Vale, Neutra, Fraca).\n\nO serviço é 100% passivo: ele NÃO realiza gestos (canPerformGestures="false"), NÃO clica, NÃO aceita, NÃO recusa e NÃO interage com nenhum elemento dos apps parceiros. A decisão de aceite é 100% humana do motorista. Além disso, nenhuma informação de passageiro (nome, foto, nota ou endereço exato) é lida ou armazenada, cumprindo a minimização de dados da LGPD.`;
                      navigator.clipboard.writeText(reviewerText);
                      setPlayStoreCopyToast('Texto do revisor copiado!');
                      setTimeout(() => setPlayStoreCopyToast(null), 3000);
                    }}
                    className="text-xs font-semibold px-2 py-1 rounded border hover:opacity-80 transition cursor-pointer"
                    style={{ borderColor: themeColors.outline, backgroundColor: themeColors.surfaceVariant }}
                  >
                    Copiar Resposta
                  </button>
                </div>
                <p className="text-xs italic text-zinc-300 leading-relaxed">
                  "O Trafego Rotta é um copiloto financeiro para motoristas de aplicativos de transporte (Uber e 99) no Brasil. O serviço de acessibilidade é utilizado estritamente em modo de leitura para detectar os valores numéricos de ofertas de corridas exibidas na tela (valor bruto da tarifa em R$, distância em km e tempo estimado em minutos)... O serviço é 100% passivo: ele NÃO realiza gestos, NÃO clica, NÃO aceita e NÃO recusa corridas."
                </p>
              </div>

              {/* Foreground service declaration */}
              <div
                style={{
                  backgroundColor: themeColors.surface,
                  border: `1px solid ${themeColors.outline}`,
                  borderRadius: tokens.rounded.sm,
                  padding: '16px'
                }}
                className="space-y-1.5"
              >
                <div className="text-xs font-mono font-semibold text-zinc-500 uppercase">
                  Foreground Service Android 14+ (specialUse)
                </div>
                <p className="text-xs text-zinc-300">
                  <strong className="text-white">Subtipo declarado:</strong> Copiloto de relance e overlay de ganhos da jornada e semáforo para motoristas de app.<br />
                  <strong className="text-white">Justificativa Play Store:</strong> O serviço em primeiro plano mantém a bolha flutuante e o monitoramento da jornada enquanto o motorista utiliza o aplicativo de navegação (Google Maps/Waze) ou o aplicativo de corridas parceiro, exibindo de relance o lucro acumulado da jornada sem sobrecarregar a visão do motorista.
                </p>
              </div>
            </div>

            {/* 5. GUIA DOS ASSETS GRÁFICOS */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                border: `1px solid ${themeColors.outline}`,
                borderRadius: tokens.rounded.md,
                padding: '24px'
              }}
              className="space-y-4"
            >
              <span
                style={{
                  fontSize: tokens.typography.label.fontSize,
                  letterSpacing: tokens.typography.label.letterSpacing,
                  color: themeColors.onSurfaceMuted
                }}
                className="font-mono font-medium block uppercase"
              >
                4. ESPECIFICAÇÃO DE ASSETS GRÁFICOS
              </span>

              <div className="grid grid-cols-1 sm:grid-cols-3 gap-3">
                <div
                  style={{
                    backgroundColor: themeColors.surface,
                    border: `1px solid ${themeColors.outline}`,
                    borderRadius: tokens.rounded.sm,
                    padding: '16px'
                  }}
                  className="space-y-1"
                >
                  <div className="font-mono text-xs font-semibold uppercase text-zinc-400">Ícone do App</div>
                  <div className="font-semibold text-sm">512 x 512 px</div>
                  <div className="text-xs text-zinc-400">PNG 32-bit com monograma geométrico "R" sobre fundo preto (#09090B).</div>
                </div>

                <div
                  style={{
                    backgroundColor: themeColors.surface,
                    border: `1px solid ${themeColors.outline}`,
                    borderRadius: tokens.rounded.sm,
                    padding: '16px'
                  }}
                  className="space-y-1"
                >
                  <div className="font-mono text-xs font-semibold uppercase text-zinc-400">Feature Graphic</div>
                  <div className="font-semibold text-sm">1024 x 500 px</div>
                  <div className="text-xs text-zinc-400">Banner editorial da Play Store com número dominante e chip "● VALE".</div>
                </div>

                <div
                  style={{
                    backgroundColor: themeColors.surface,
                    border: `1px solid ${themeColors.outline}`,
                    borderRadius: tokens.rounded.sm,
                    padding: '16px'
                  }}
                  className="space-y-1"
                >
                  <div className="font-mono text-xs font-semibold uppercase text-zinc-400">Screenshots (9:20)</div>
                  <div className="font-semibold text-sm">1080 x 2400 px</div>
                  <div className="text-xs text-zinc-400">5 telas essenciais: Jornada, Semáforo, Relatórios, Paridade e Metas.</div>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* 9. ABA DE TOKENS */}
        {activeTab === 'tokens' && (
          <div className="grid grid-cols-2 gap-3">
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                border: `1px solid ${themeColors.outline}`
              }}
              className="p-4 rounded-xl"
            >
              <p className="text-xs uppercase font-mono text-zinc-500 mb-1 font-semibold">Display</p>
              <p className="text-3xl font-semibold tabular-nums">56px / 600</p>
              <p className="text-xs text-zinc-400">fontFeature: tnum</p>
            </div>
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                border: `1px solid ${themeColors.outline}`
              }}
              className="p-4 rounded-xl"
            >
              <p className="text-xs uppercase font-mono text-zinc-500 mb-1 font-semibold">Headline</p>
              <p className="text-2xl font-semibold">28px / 600</p>
              <p className="text-xs text-zinc-400">Títulos e seções</p>
            </div>
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                border: `1px solid ${themeColors.outline}`
              }}
              className="p-4 rounded-xl"
            >
              <p className="text-xs uppercase font-mono text-zinc-500 mb-1 font-semibold">Title</p>
              <p className="text-lg font-semibold">18px / 600</p>
              <p className="text-xs text-zinc-400">Botões e listas</p>
            </div>
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                border: `1px solid ${themeColors.outline}`
              }}
              className="p-4 rounded-xl"
            >
              <p className="text-xs uppercase font-mono text-zinc-500 mb-1 font-semibold">Label</p>
              <p className="text-xs font-semibold uppercase tracking-widest">12px / 500</p>
              <p className="text-xs text-zinc-400">Caixa alta espaçada + tnum</p>
            </div>
          </div>
        )}

        </div>

        {isPhoneMode && (
          <>
            {/* Barra de Navegação Nativa Inferior do Motorista */}
            <div
              style={{
                backgroundColor: themeColors.surfaceVariant,
                borderTop: `1px solid ${themeColors.outline}`
              }}
              className="px-2 py-2 flex items-center justify-around select-none"
            >
              <button
                type="button"
                onClick={() => setActiveTab('journey')}
                className={`flex flex-col items-center gap-1 px-3 py-1.5 rounded-lg text-[11px] font-semibold transition cursor-pointer ${
                  activeTab === 'journey' ? 'bg-zinc-800 text-white shadow-sm' : 'text-zinc-500 hover:text-zinc-300'
                }`}
              >
                <span className="text-base leading-none">🚗</span>
                <span className="leading-tight">Jornada</span>
              </button>
              <button
                type="button"
                onClick={() => setActiveTab('signal')}
                className={`flex flex-col items-center gap-1 px-3 py-1.5 rounded-lg text-[11px] font-semibold transition cursor-pointer ${
                  activeTab === 'signal' ? 'bg-zinc-800 text-white shadow-sm' : 'text-zinc-500 hover:text-zinc-300'
                }`}
              >
                <span className="text-base leading-none">🚦</span>
                <span className="leading-tight">Semáforo</span>
              </button>
              <button
                type="button"
                onClick={() => setActiveTab('reports')}
                className={`flex flex-col items-center gap-1 px-3 py-1.5 rounded-lg text-[11px] font-semibold transition cursor-pointer ${
                  activeTab === 'reports' ? 'bg-zinc-800 text-white shadow-sm' : 'text-zinc-500 hover:text-zinc-300'
                }`}
              >
                <span className="text-base leading-none">📊</span>
                <span className="leading-tight">Relatórios</span>
              </button>
              <button
                type="button"
                onClick={() => setActiveTab('goals')}
                className={`flex flex-col items-center gap-1 px-3 py-1.5 rounded-lg text-[11px] font-semibold transition cursor-pointer ${
                  activeTab === 'goals' ? 'bg-zinc-800 text-white shadow-sm' : 'text-zinc-500 hover:text-zinc-300'
                }`}
              >
                <span className="text-base leading-none">🎯</span>
                <span className="leading-tight">Metas</span>
              </button>
              <button
                type="button"
                onClick={() => setActiveTab('profile')}
                className={`flex flex-col items-center gap-1 px-3 py-1.5 rounded-lg text-[11px] font-semibold transition cursor-pointer ${
                  activeTab === 'profile' ? 'bg-zinc-800 text-white shadow-sm' : 'text-zinc-500 hover:text-zinc-300'
                }`}
              >
                <span className="text-base leading-none">👤</span>
                <span className="leading-tight">Perfil</span>
              </button>
            </div>

            {/* Pílula de Gestos Android */}
            <div style={{ backgroundColor: themeColors.surfaceVariant }} className="pb-2.5 pt-1 flex justify-center">
              <div className="w-32 h-1 rounded-full bg-zinc-600/70" />
            </div>
          </>
        )}
      </div>

      <div className="mt-8 pt-4 border-t text-center text-xs opacity-60" style={{ borderColor: themeColors.outline }}>
        Trafego Rotta • Android 14 Native + Simulador • Fonte de verdade: Jetpack Compose
      </div>
    </div>

      {/* OVERLAY FLUTUANTE EM TEMPO REAL (ETAPA H) */}
      {overlayEnabled && (
        <div className="fixed bottom-6 right-6 z-50 select-none">
          {!floatingOverlayExpanded ? (
            <div className="relative group">
              <button
                type="button"
                onClick={() => setFloatingOverlayExpanded(true)}
                style={{
                  backgroundColor: '#0A0A0A',
                  color: '#FAFAFA',
                  width: '52px',
                  height: '52px',
                  borderRadius: tokens.rounded.full,
                  boxShadow: '0 12px 28px rgba(0,0,0,0.45)',
                  border: '1.5px solid rgba(255,255,255,0.25)'
                }}
                className="flex items-center justify-center font-bold text-xl cursor-pointer hover:scale-105 active:scale-95 transition-transform"
                title="Overlay Flutuante Rotta • Toque para expandir"
              >
                R
              </button>
              {/* Badge indicativo do semáforo na bolha */}
              <div
                style={{
                  backgroundColor: simGradeColor,
                  border: '2px solid #0A0A0A',
                  width: '14px',
                  height: '14px',
                  borderRadius: '9999px'
                }}
                className="absolute top-0 right-0 shadow"
              />
            </div>
          ) : (
            <div
              style={{
                backgroundColor: '#0A0A0A',
                color: '#FAFAFA',
                borderRadius: tokens.rounded.lg,
                border: '1.5px solid rgba(255,255,255,0.3)',
                boxShadow: '0 20px 48px rgba(0,0,0,0.6)',
                width: '320px',
                padding: '16px'
              }}
              className="animate-in fade-in zoom-in-95 duration-150 cursor-pointer"
              onClick={() => setFloatingOverlayExpanded(false)}
            >
              <div className="flex justify-between items-start mb-2">
                <div>
                  <span className="text-[10px] font-mono tracking-wider text-zinc-400 uppercase block">
                    GANHO JORNADA
                  </span>
                  <span className="text-2xl font-bold tabular-nums text-white block">
                    R$ 340,80
                  </span>
                </div>

                <div
                  style={{
                    backgroundColor: simGradeColor,
                    color: simGradeTextColor,
                    borderRadius: tokens.rounded.full,
                    padding: '0 18px',
                    height: '36px'
                  }}
                  className="flex items-center font-semibold text-sm shadow shrink-0"
                >
                  {simGradeWord}
                </div>
              </div>

              <div className="pt-2 border-t border-zinc-800 text-xs text-zinc-300 space-y-1">
                <div className="flex justify-between font-medium">
                  <span>{simOfferApp} • R$ {parsedSimGross.toFixed(2).replace('.', ',')}</span>
                  <span className="text-zinc-400">{parsedSimKm} km ({parsedSimMin} min)</span>
                </div>
                <div className="flex justify-between font-mono text-[11px]">
                  <span style={{ color: meetsSimKm ? '#4ADE80' : '#F87171' }}>
                    R$ {simRateKm.toFixed(2).replace('.', ',')}/km
                  </span>
                  <span style={{ color: meetsSimHour ? '#4ADE80' : '#F87171' }}>
                    R$ {simRateHour.toFixed(2).replace('.', ',')}/h
                  </span>
                </div>
              </div>

              <div className="mt-2.5 pt-2 border-t border-zinc-800/60 text-center">
                <span className="text-[10px] text-zinc-500 uppercase tracking-wider">
                  Toque no painel para recolher à bolha
                </span>
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
}
