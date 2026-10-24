# 🚣 BoatMod — Barco Personalizável

> **Adicione quantos remos quiser no seu barco e instale motores de baiacu para soltar jatos de vento!** Inspirado no Minecraft vanilla, mas muito mais divertido.

![Minecraft 1.20.1](https://img.shields.io/badge/Minecraft-1.20.1-brightgreen?style=for-the-badge)
![Fabric](https://img.shields.io/badge/Loader-Fabric-DBD0B4?style=for-the-badge)
![License](https://img.shields.io/badge/Licença-MIT-blue?style=for-the-badge)

### 🎥 Inspiração
Este mod foi criado a partir de 3 conceitos:
- 🛩️ **Barco turbo** com jato duplo de vento branco (propulsão de baiacu)
- 🛶 **Barco vanilla** com 1 remo
- 🚣 **Barco com 8 remos** (4 de cada lado) — super veloz!

---

## ✨ Funcionalidades

| Função | Como usar | Efeito |
|--------|-----------|--------|
| **➕ Adicionar Remo** | Segure um **Remo** (`boatmod:oar`) ou **Graveto** e `Clique Direito` no barco | +1 remo visual + **+15% velocidade** (até 12 remos = +165%!) |
| **➖ Remover Remo** | `Shift + Clique Direito` com mão vazia | Remove 1 remo e devolve o item |
| **🐡 Adicionar Baiacu** | Segure **Baiacu** (`Pufferfish`) e `Clique Direito` no barco | Instala propulsor (até 4) |
| **💨 TURBO** | Dentro do barco, aperte **`G`** | Jato de vento branco + partículas `CLOUD` + impulso! Quanto mais baiacus, mais forte |

### 📊 Velocidades

```
1 remo  = 8.0 blocos/s  (vanilla)
4 remos = 12.2 blocos/s (+52%)
8 remos = 18.4 blocos/s (+130%) ← igual Imagem 3
12 remos = 21.2 blocos/s (máx.)

1 baiacu = impulso 12 blocos, cooldown 3s
4 baiacus = impulso 28 blocos, cooldown 1.5s + jato gigante
```

---

## 📥 Instalação (Jogador)

1. Instale **Fabric Loader 0.15.7+** para 1.20.1: https://fabricmc.net/use/
2. Baixe **Fabric API 0.92.0+1.20.1**: https://modrinth.com/mod/fabric-api
3. Baixe `boatmod-fabric-1.0.0.jar` da aba **Releases** deste repositório
4. Coloque os dois `.jar` em `.minecraft/mods/`
5. Jogue! Dê `/give @p boatmod:oar 64` e `/give @p minecraft:pufferfish 64` para testar

---

## 🔨 Receitas

### Remo
```
 T
 S
 T
```
`T` = Tábua (qualquer), `S` = Graveto → 1x `boatmod:oar`

> Dica: Você também pode usar **Graveto** ou **Pá de Madeira** direto no barco para testar sem craftar!

---

## 🛠️ Instalação para Desenvolvedores

```bash
git clone https://github.com/SEUUSERNAME/boatmod.git
cd boatmod
./gradlew genSources        # gera sources para IDE
./gradlew build             # compila o mod
# jar em build/libs/boatmod-fabric-1.0.0.jar
```

**Requisitos:** JDK 17, IntelliJ IDEA / VS Code

### Estrutura do projeto

```
src/main/java/com/boatmod/
├── BoatModFabric.java   # Lógica principal + evento de clique + turbo
├── BoatModClient.java   # Keybind G (cliente)
├── ModItems.java        # Registro do item Remo
└── mixin/BoatMixin.java # Injeta bônus de velocidade no tick do barco

src/main/resources/
├── fabric.mod.json
├── boatmod.mixins.json
└── assets/boatmod/lang/
```

---

## 🎮 Como jogar (passo a passo)

1. Coloque um **barco** na água
2. Segure **Remo** e clique direito no barco → mensagem `+1 Remo!`
3. Repita até ter **8 remos** (4 cada lado) — seu barco vai ficar igual a Imagem 3
4. Segure **Baiacu** e clique direito → `+1 Baiacu Propulsor!`
5. Entre no barco e aperte **`G`** → **WHOOSH!** 💨💨💨

---

## ⚙️ Configuração

Edite `config/boatmod.json` (gerado após primeira execução):

```json
{
  "maxOars": 12,
  "speedPerOar": 0.15,
  "maxPuffers": 4,
  "turboPower": 1.8
}
```

---

## 🤝 Contribuindo

PRs são bem-vindos! Veja `CONTRIBUTING.md`.

1. Fork → `git checkout -b feat/minha-ideia`
2. Commit → `git commit -m "feat: adiciona ..."`
3. Push → `git push`
4. Abra um Pull Request

---

## 📄 Licença

MIT — veja `LICENSE`. Sinta-se livre para usar em modpacks!

---

## 🙏 Créditos

- Inspiração: suas imagens de barco com 1 remo / 8 remos / jato turbo
- Fabric Loader, Fabric API, Yarn Mappings
- Feito com ❤️ em Goiânia, GO — Brasil

**Curtiu? Deixe uma ⭐ no GitHub!**
