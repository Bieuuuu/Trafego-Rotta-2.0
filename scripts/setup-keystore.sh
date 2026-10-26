#!/usr/bin/env bash
set -e

KEYSTORE_NAME="release.keystore"
PROPERTIES_FILE="keystore.properties"
ALIAS_NAME="traffigorotta"

echo "========================================================"
echo "   Trafego Rotta — Gerador de Chave de Release Android"
echo "========================================================"

if [ -f "$KEYSTORE_NAME" ]; then
    echo "ℹ️  O arquivo '$KEYSTORE_NAME' já existe."
else
    echo "Gerando keystore de produção RSA 2048 bits..."
    read -s -p "Digite a senha para a Keystore: " KEY_PASS
    echo ""
    read -s -p "Confirme a senha: " KEY_PASS_CONFIRM
    echo ""

    if [ "$KEY_PASS" != "$KEY_PASS_CONFIRM" ]; then
        echo "❌ As senhas não conferem. Abortando."
        exit 1
    fi

    if [ -z "$KEY_PASS" ]; then
        echo "❌ A senha não pode ser vazia. Abortando."
        exit 1
    fi

    keytool -genkeypair \
        -v \
        -keystore "$KEYSTORE_NAME" \
        -alias "$ALIAS_NAME" \
        -keyalg RSA \
        -keysize 2048 \
        -validity 10000 \
        -storepass "$KEY_PASS" \
        -keypass "$KEY_PASS" \
        -dname "CN=Trafego Rotta, OU=Engenharia, O=Trafego Rotta, L=Sao Paulo, ST=SP, C=BR"

    echo "✓ Arquivo '$KEYSTORE_NAME' gerado com sucesso!"
fi

# Criação do keystore.properties se não existir
if [ ! -f "$PROPERTIES_FILE" ]; then
    cat <<EOF > "$PROPERTIES_FILE"
# Configuração de Release (Trafego Rotta)
# NUNCA envie este arquivo nem o .keystore para o git!
storeFile=../release.keystore
storePassword=${KEY_PASS:-SuaSenhaAqui}
keyAlias=${ALIAS_NAME}
keyPassword=${KEY_PASS:-SuaSenhaAqui}
EOF
    echo "✓ Arquivo '$PROPERTIES_FILE' configurado e protegido pelo .gitignore!"
else
    echo "ℹ️  O arquivo '$PROPERTIES_FILE' já existe."
fi

echo ""
echo "========================================================"
echo "Pronto! Agora execute para gerar o pacote para a Play Store:"
echo "   ./gradlew :app:bundleRelease"
echo "========================================================"
