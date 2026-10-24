# Como postar no GitHub

## 1. Criar repositório

1. Vá em https://github.com/new
2. Nome: `boatmod` (ou `boatmod-fabric`)
3. Descrição: `Barco personalizável com remos e turbo de baiacu para Minecraft 1.20.1 Fabric`
4. Marque **Public** + `Add README` (pode pular, já temos)
5. Clique **Create repository**

## 2. Enviar este projeto

No terminal, dentro da pasta `boatmod-fabric-1.20.1`:

```bash
git init
git add .
git commit -m "feat: initial BoatMod Fabric 1.20.1 - remos + turbo"
git branch -M main
git remote add origin https://github.com/SEUUSERNAME/boatmod.git
git push -u origin main
```

## 3. Criar Release (para distribuir o .jar)

1. No GitHub, vá em **Releases** → **Draft a new release**
2. Tag: `v1.0.0`
3. Title: `v1.0.0 - Barco com 8 remos + Turbo`
4. Descrição: copie do README
5. Anexe o arquivo `boatmod-fabric-1.0.0.jar` (gerado com `./gradlew build`)
6. Marque **Latest** e **Publish release**

## 4. Configurar sobre o repo

- Em **About** (canto direito), clique na engrenagem:
  - Description: `🚣 Minecraft Fabric mod - Adicione remos e motores de baiacu no barco!`
  - Website: link do Modrinth/CurseForge se publicar
  - Topics: `minecraft`, `fabric`, `minecraft-mod`, `boat`, `1.20.1`

## 5. (Opcional) Publicar no Modrinth

1. https://modrinth.com → Create project
2. Loader: Fabric, Version: 1.20.1
3. Faça upload do .jar gerado
4. Link de volta no README

Pronto! Seu mod está no ar 🚀
