import fs from 'fs';
import path from 'path';
import * as yaml from 'js-yaml';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const rootDir = path.resolve(__dirname, '..');

// 1. Ler DESIGN.md e extrair YAML frontmatter
const designPath = path.join(rootDir, 'DESIGN.md');
const content = fs.readFileSync(designPath, 'utf-8');
const match = content.match(/^---\n([\s\S]+?)\n---/);

if (!match) {
  console.error('Frontmatter YAML não encontrado no DESIGN.md');
  process.exit(1);
}

const tokens = yaml.load(match[1]);
console.log('Tokens carregados com sucesso do DESIGN.md:');
console.log(`- Cores: ${Object.keys(tokens.colors).length}`);
console.log(`- Tipografia: ${Object.keys(tokens.typography).length}`);
console.log(`- Formas: ${Object.keys(tokens.rounded).length}`);
console.log(`- Espaçamentos: ${Object.keys(tokens.spacing).length}`);

function hexToColorInt(hex) {
  const clean = hex.replace('#', '');
  return `0xFF${clean.toUpperCase()}`;
}

// 2. Gerar Color.kt
const colorKt = `// Gerado automaticamente por scripts/generate-tokens.js a partir do DESIGN.md
package com.traffigorotta.core.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

// Light mode tokens - DESIGN.md
val RottaBlack = Color(${hexToColorInt(tokens.colors['primary'])})
val RottaWhite = Color(${hexToColorInt(tokens.colors['surface'])})
val RottaSurfaceVariantLight = Color(${hexToColorInt(tokens.colors['surface-variant'])})
val RottaMutedLight = Color(${hexToColorInt(tokens.colors['on-surface-muted'])})
val RottaOutlineLight = Color(${hexToColorInt(tokens.colors['outline'])})
val RottaInverseSurfaceLight = Color(${hexToColorInt(tokens.colors['inverse-surface'])})
val RottaOnInverseSurfaceLight = Color(${hexToColorInt(tokens.colors['on-inverse-surface'])})

// Dark mode tokens
val RottaBackgroundDark = Color(${hexToColorInt(tokens.colors['primary'])})
val RottaSurfaceDark = Color(${hexToColorInt(tokens.colors['primary'])})
val RottaSurfaceVariantDark = Color(0xFF18181B)
val RottaOnSurfaceDark = Color(0xFFFFFFFF)
val RottaMutedDark = Color(0xFFA1A1AA)
val RottaOutlineDark = Color(0xFF27272A)
val RottaInverseSurfaceDark = Color(0xFFFAFAFA)
val RottaOnInverseSurfaceDark = Color(0xFF0A0A0A)

// Signal colors (Semáforo e Metas) - DESIGN.md
val RottaSignalGood = Color(${hexToColorInt(tokens.colors['signal-good'])})
val RottaOnSignalGood = Color(${hexToColorInt(tokens.colors['on-signal-good'])})

val RottaSignalMedium = Color(${hexToColorInt(tokens.colors['signal-medium'])})
val RottaOnSignalMedium = Color(${hexToColorInt(tokens.colors['on-signal-medium'])})

val RottaSignalBad = Color(${hexToColorInt(tokens.colors['signal-bad'])})
val RottaOnSignalBad = Color(${hexToColorInt(tokens.colors['on-signal-bad'])})

@Immutable
data class RottaColors(
    val primary: Color,
    val onPrimary: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val onSurface: Color,
    val onSurfaceMuted: Color,
    val outline: Color,
    val inverseSurface: Color,
    val onInverseSurface: Color,
    val signalGood: Color = RottaSignalGood,
    val onSignalGood: Color = RottaOnSignalGood,
    val signalMedium: Color = RottaSignalMedium,
    val onSignalMedium: Color = RottaOnSignalMedium,
    val signalBad: Color = RottaSignalBad,
    val onSignalBad: Color = RottaOnSignalBad,
    val isDark: Boolean = false
)

val LightRottaColors = RottaColors(
    primary = RottaBlack,
    onPrimary = RottaWhite,
    surface = RottaWhite,
    surfaceVariant = RottaSurfaceVariantLight,
    onSurface = RottaBlack,
    onSurfaceMuted = RottaMutedLight,
    outline = RottaOutlineLight,
    inverseSurface = RottaInverseSurfaceLight,
    onInverseSurface = RottaOnInverseSurfaceLight,
    isDark = false
)

val DarkRottaColors = RottaColors(
    primary = Color(0xFFFAFAFA),
    onPrimary = RottaBlack,
    surface = RottaSurfaceDark,
    surfaceVariant = RottaSurfaceVariantDark,
    onSurface = RottaOnSurfaceDark,
    onSurfaceMuted = RottaMutedDark,
    outline = RottaOutlineDark,
    inverseSurface = RottaInverseSurfaceDark,
    onInverseSurface = RottaOnInverseSurfaceDark,
    isDark = true
)
`;

// 3. Gerar Type.kt com Geist Font Family embarcada e tnum obrigatório
const typeKt = `// Gerado automaticamente por scripts/generate-tokens.js a partir do DESIGN.md
package com.traffigorotta.core.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.traffigorotta.core.design.R

// Família de fontes Geist embarcada diretamente no aplicativo (res/font/)
val GeistFontFamily = FontFamily(
    Font(R.font.geist_regular, FontWeight.Normal),
    Font(R.font.geist_medium, FontWeight.Medium),
    Font(R.font.geist_semibold, FontWeight.SemiBold),
    Font(R.font.geist_bold, FontWeight.Bold)
)

@Immutable
data class RottaTypography(
    // Display (56px, 600, 1.05 line height, -0.03em, tnum)
    val display: TextStyle = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 56.sp,
        lineHeight = (56 * 1.05).sp,
        letterSpacing = (-0.03).em,
        fontFeatureSettings = "tnum"
    ),
    // Headline (28px, 600, 1.15 line height, -0.02em)
    val headline: TextStyle = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = (28 * 1.15).sp,
        letterSpacing = (-0.02).em
    ),
    // Title (18px, 600, 1.3 line height)
    val title: TextStyle = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = (18 * 1.3).sp
    ),
    // Body (16px, 400, 1.5 line height)
    val body: TextStyle = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = (16 * 1.5).sp
    ),
    // Body-sm (14px, 400, 1.45 line height)
    val bodySm: TextStyle = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = (14 * 1.45).sp
    ),
    // Label (12px, 500, 1.3 line height, 0.06em letter spacing, tnum)
    val label: TextStyle = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = (12 * 1.3).sp,
        letterSpacing = 0.06.em,
        fontFeatureSettings = "tnum"
    )
)
`;

// 4. Gerar Shape.kt
const shapeKt = `// Gerado automaticamente por scripts/generate-tokens.js a partir do DESIGN.md
package com.traffigorotta.core.design.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

@Immutable
data class RottaShapes(
    val sm: CornerBasedShape = RoundedCornerShape(${parseInt(tokens.rounded['sm'], 10)}.dp),
    val md: CornerBasedShape = RoundedCornerShape(${parseInt(tokens.rounded['md'], 10)}.dp),
    val lg: CornerBasedShape = RoundedCornerShape(${parseInt(tokens.rounded['lg'], 10)}.dp),
    val full: CornerBasedShape = CircleShape
)
`;

// 5. Gerar Spacing.kt
const spacingKt = `// Gerado automaticamente por scripts/generate-tokens.js a partir do DESIGN.md
package com.traffigorotta.core.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class RottaSpacing(
    val xs: Dp = ${parseInt(tokens.spacing['xs'], 10)}.dp,
    val sm: Dp = ${parseInt(tokens.spacing['sm'], 10)}.dp,
    val md: Dp = ${parseInt(tokens.spacing['md'], 10)}.dp,
    val lg: Dp = ${parseInt(tokens.spacing['lg'], 10)}.dp,
    val xl: Dp = ${parseInt(tokens.spacing['xl'], 10)}.dp,
    val gutter: Dp = ${parseInt(tokens.spacing['gutter'], 10)}.dp
)
`;

// Salvar arquivos Kotlin
const themeDir = path.join(rootDir, 'core/design/src/main/java/com/traffigorotta/core/design/theme');
fs.writeFileSync(path.join(themeDir, 'Color.kt'), colorKt);
fs.writeFileSync(path.join(themeDir, 'Type.kt'), typeKt);
fs.writeFileSync(path.join(themeDir, 'Shape.kt'), shapeKt);
fs.writeFileSync(path.join(themeDir, 'Spacing.kt'), spacingKt);
console.log('Arquivos Kotlin gerados em core:design/theme.');

// 6. Gerar design-preview/tokens.ts na raiz
const previewDir = path.join(rootDir, 'design-preview');
fs.mkdirSync(previewDir, { recursive: true });

const previewTokensTs = `// Gerado automaticamente a partir de DESIGN.md
export const tokens = {
  colors: {
    primary: "${tokens.colors['primary']}",
    onPrimary: "${tokens.colors['on-primary']}",
    surface: "${tokens.colors['surface']}",
    surfaceVariant: "${tokens.colors['surface-variant']}",
    onSurface: "${tokens.colors['on-surface']}",
    onSurfaceMuted: "${tokens.colors['on-surface-muted']}",
    outline: "${tokens.colors['outline']}",
    inverseSurface: "${tokens.colors['inverse-surface']}",
    onInverseSurface: "${tokens.colors['on-inverse-surface']}",
    signalGood: "${tokens.colors['signal-good']}",
    onSignalGood: "${tokens.colors['on-signal-good']}",
    signalMedium: "${tokens.colors['signal-medium']}",
    onSignalMedium: "${tokens.colors['on-signal-medium']}",
    signalBad: "${tokens.colors['signal-bad']}",
    onSignalBad: "${tokens.colors['on-signal-bad']}",
    dark: {
      primary: "#FAFAFA",
      onPrimary: "#0A0A0A",
      surface: "#0A0A0A",
      surfaceVariant: "#18181B",
      onSurface: "#FFFFFF",
      onSurfaceMuted: "#A1A1AA",
      outline: "#27272A",
      inverseSurface: "#FAFAFA",
      onInverseSurface: "#0A0A0A",
    }
  },
  typography: {
    display: {
      fontSize: "${tokens.typography['display'].fontSize}",
      fontWeight: "${tokens.typography['display'].fontWeight}",
      lineHeight: "${tokens.typography['display'].lineHeight}",
      letterSpacing: "${tokens.typography['display'].letterSpacing}",
      fontFeatureSettings: "${tokens.typography['display'].fontFeature || 'tnum'}"
    },
    headline: {
      fontSize: "${tokens.typography['headline'].fontSize}",
      fontWeight: "${tokens.typography['headline'].fontWeight}",
      lineHeight: "${tokens.typography['headline'].lineHeight}",
      letterSpacing: "${tokens.typography['headline'].letterSpacing}"
    },
    title: {
      fontSize: "${tokens.typography['title'].fontSize}",
      fontWeight: "${tokens.typography['title'].fontWeight}",
      lineHeight: "${tokens.typography['title'].lineHeight}"
    },
    body: {
      fontSize: "${tokens.typography['body'].fontSize}",
      fontWeight: "${tokens.typography['body'].fontWeight}",
      lineHeight: "${tokens.typography['body'].lineHeight}"
    },
    bodySm: {
      fontSize: "${tokens.typography['body-sm'].fontSize}",
      fontWeight: "${tokens.typography['body-sm'].fontWeight}",
      lineHeight: "${tokens.typography['body-sm'].lineHeight}"
    },
    label: {
      fontSize: "${tokens.typography['label'].fontSize}",
      fontWeight: "${tokens.typography['label'].fontWeight}",
      lineHeight: "${tokens.typography['label'].lineHeight}",
      letterSpacing: "${tokens.typography['label'].letterSpacing}",
      fontFeatureSettings: "${tokens.typography['label'].fontFeature || 'tnum'}"
    }
  },
  rounded: {
    sm: "${tokens.rounded['sm']}",
    md: "${tokens.rounded['md']}",
    lg: "${tokens.rounded['lg']}",
    full: "${tokens.rounded['full']}"
  },
  spacing: {
    xs: "${tokens.spacing['xs']}",
    sm: "${tokens.spacing['sm']}",
    md: "${tokens.spacing['md']}",
    lg: "${tokens.spacing['lg']}",
    xl: "${tokens.spacing['xl']}",
    gutter: "${tokens.spacing['gutter']}"
  }
};
`;

const previewCss = `/* Gerado automaticamente a partir de DESIGN.md */
:root {
  --rotta-primary: ${tokens.colors['primary']};
  --rotta-on-primary: ${tokens.colors['on-primary']};
  --rotta-surface: ${tokens.colors['surface']};
  --rotta-surface-variant: ${tokens.colors['surface-variant']};
  --rotta-on-surface: ${tokens.colors['on-surface']};
  --rotta-on-surface-muted: ${tokens.colors['on-surface-muted']};
  --rotta-outline: ${tokens.colors['outline']};
  --rotta-signal-good: ${tokens.colors['signal-good']};
  --rotta-signal-medium: ${tokens.colors['signal-medium']};
  --rotta-signal-bad: ${tokens.colors['signal-bad']};
}

.dark {
  --rotta-primary: #FAFAFA;
  --rotta-on-primary: #0A0A0A;
  --rotta-surface: #0A0A0A;
  --rotta-surface-variant: #18181B;
  --rotta-on-surface: #FFFFFF;
  --rotta-on-surface-muted: #A1A1AA;
  --rotta-outline: #27272A;
}
`;

fs.writeFileSync(path.join(previewDir, 'tokens.ts'), previewTokensTs);
fs.writeFileSync(path.join(previewDir, 'tokens.css'), previewCss);
console.log('design-preview/tokens.ts e tokens.css gerados na raiz.');
