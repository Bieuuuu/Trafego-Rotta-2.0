#!/usr/bin/env bash
set -e

echo "=================================================="
echo "    Trafego Rotta — Build de Release (.aab / APK)"
echo "=================================================="

# Verifica se existe keystore configurada
if [ ! -f "keystore.properties" ] && [ -z "$KEYSTORE_PATH" ]; then
    echo ""
    echo "⚠️  'keystore.properties' não encontrado na raiz."
    echo ""
    echo "Passo a passo para gerar sua chave:"
    echo "1. Execute no terminal:"
    echo "   keytool -genkey -v -keystore release.keystore -alias traffigorotta -keyalg RSA -keysize 2048 -validity 10000"
    echo ""
    echo "2. Copie o arquivo de exemplo:"
    echo "   cp keystore.properties.example keystore.properties"
    echo ""
    echo "3. Preencha as senhas no keystore.properties recém-criado."
    echo "=================================================="
fi

echo ""
echo "Comandos disponíveis:"
echo "• Gerar Bundle (.aab) para o Play Console:"
echo "  ./gradlew :app:bundleRelease"
echo ""
echo "• Gerar APK assinado direto para teste no aparelho:"
echo "  ./gradlew :app:assembleRelease"
echo "=================================================="
