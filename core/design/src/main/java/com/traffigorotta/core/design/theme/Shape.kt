// Gerado automaticamente por scripts/generate-tokens.js a partir do DESIGN.md
package com.traffigorotta.core.design.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

@Immutable
data class RottaShapes(
    val sm: CornerBasedShape = RoundedCornerShape(8.dp),
    val md: CornerBasedShape = RoundedCornerShape(16.dp),
    val lg: CornerBasedShape = RoundedCornerShape(28.dp),
    val full: CornerBasedShape = CircleShape
)
