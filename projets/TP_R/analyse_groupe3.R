# ============================================================

# --- Installation et chargement des packages ---
packages <- c("ggplot2", "corrplot", "FactoMineR", "factoextra",
              "cluster", "caret", "e1071", "ROCR", "car", "gridExtra")
installed <- packages %in% rownames(installed.packages())
if (any(!installed)) install.packages(packages[!installed])
lapply(packages, library, character.only = TRUE)

# --- Chargement des données ---
df <- read.csv("C:/Users/user/Desktop/Analyse des données/data_finance.csv")
cat("Dimensions :", nrow(df), "lignes x", ncol(df), "colonnes\n")
cat("Variables :", names(df), "\n")

# ===========================================================
# PARTIE 1 : EXPLORATION DES DONNÉES
# ===========================================================

# 1.1 Résumé statistique
cat("\n===== RÉSUMÉ STATISTIQUE =====\n")
summary(df)

# Statistiques complémentaires
cat("\n--- Écart-type ---\n")
sapply(df[, -6], sd)

cat("\n--- Coefficient de variation (%) ---\n")
cv <- sapply(df[, -6], function(x) sd(x)/mean(x)*100)
round(cv, 2)

cat("\n--- Asymétrie (Skewness) ---\n")
sapply(df[, -6], function(x) {
  n <- length(x)
  m <- mean(x)
  s <- sd(x)
  (sum((x - m)^3) / n) / s^3
})

# 1.2 Distribution de la variable cible
cat("\n--- Distribution de perf ---\n")
table(df$perf)
prop.table(table(df$perf)) * 100

# 1.3 Graphiques des distributions
par(mfrow = c(2, 3))
vars <- c("rendement", "volatilite", "volume", "beta", "dividende")
for (v in vars) {
  hist(df[[v]], main = paste("Distribution de", v),
       xlab = v, col = "steelblue", border = "white",
       freq = FALSE)
  lines(density(df[[v]]), col = "red", lwd = 2)
}
par(mfrow = c(1, 1))

# Boxplots par perf
par(mfrow = c(2, 3))
for (v in vars) {
  boxplot(df[[v]] ~ df$perf,
          main = paste(v, "par performance"),
          xlab = "Performance (0=faible, 1=bonne)",
          ylab = v,
          col = c("#E74C3C", "#2ECC71"))
}
par(mfrow = c(1, 1))

# 1.4 Matrice de corrélation
cat("\n===== MATRICE DE CORRÉLATION =====\n")
cor_mat <- cor(df)
print(round(cor_mat, 3))

corrplot(cor_mat, method = "color", type = "upper",
         tl.col = "black", tl.srt = 45,
         addCoef.col = "black", number.cex = 0.8,
         title = "Matrice de corrélation - Données financières",
         mar = c(0,0,2,0))

# 1.5 Identification des variables instables
cat("\n===== VARIABLES LES PLUS INSTABLES (CV) =====\n")
print(sort(cv, decreasing = TRUE))

# ===========================================================
# PARTIE 2 : ACP (Analyse en Composantes Principales)
# ===========================================================

# 2.1 Standardisation
X <- df[, vars]
X_scaled <- scale(X)

# 2.2 ACP avec FactoMineR
cat("\n===== ACP =====\n")
pca_res <- PCA(X, scale.unit = TRUE, graph = FALSE)

# 2.3 Valeurs propres et variance expliquée
cat("\n--- Valeurs propres ---\n")
eigenvalues <- pca_res$eig
print(round(eigenvalues, 4))

# Critère de Kaiser : retenir les axes avec valeur propre > 1
n_axes <- sum(eigenvalues[, 1] > 1)
cat("\nNombre d'axes retenus (critère de Kaiser) :", n_axes, "\n")
cat("Variance expliquée cumulée par", n_axes, "axes :",
    round(eigenvalues[n_axes, 3], 2), "%\n")

# Scree plot
fviz_eig(pca_res, addlabels = TRUE, ylim = c(0, 50),
         title = "Éboulis des valeurs propres - ACP")

# 2.4 Contributions et corrélations variables-axes
cat("\n--- Coordonnées des variables sur les axes ---\n")
print(round(pca_res$var$coord, 3))

cat("\n--- Contributions des variables aux axes (%) ---\n")
print(round(pca_res$var$contrib, 3))

# Cercle des corrélations
fviz_pca_var(pca_res,
             col.var = "contrib",
             gradient.cols = c("#00AFBB", "#E7B800", "#FC4E07"),
             repel = TRUE,
             title = "Cercle des corrélations - ACP (Axes 1 et 2)")

# Graphe des individus colorés par perf
fviz_pca_ind(pca_res,
             col.ind = as.factor(df$perf),
             palette = c("#E74C3C", "#2ECC71"),
             title = "Individus dans le plan factoriel (Axes 1-2)",
             repel = FALSE)

# Biplot
fviz_pca_biplot(pca_res,
                col.ind = as.factor(df$perf),
                palette = c("#E74C3C", "#2ECC71"),
                col.var = "#2c3e50",
                title = "Biplot ACP - Groupe 3")

# ===========================================================
# PARTIE 3 : CLUSTERING
# ===========================================================

cat("\n===== CLUSTERING K-MEANS =====\n")

# 3.1 Méthode du coude pour choisir K optimal
set.seed(42)
inertie <- sapply(1:8, function(k) {
  kmeans(X_scaled, centers = k, nstart = 25, iter.max = 100)$tot.withinss
})

plot(1:8, inertie, type = "b", pch = 19, col = "steelblue",
     main = "Méthode du coude - Choix de K",
     xlab = "Nombre de clusters K", ylab = "Inertie intra-classe")
abline(v = 2, lty = 2, col = "red")

# Indice silhouette
sil_scores <- sapply(2:6, function(k) {
  km <- kmeans(X_scaled, centers = k, nstart = 25)
  mean(silhouette(km$cluster, dist(X_scaled))[, 3])
})
cat("\nIndices silhouette pour K=2 à 6 :\n")
print(round(sil_scores, 4))
cat("K optimal (silhouette max) :", which.max(sil_scores) + 1, "\n")

fviz_nbclust(X_scaled, kmeans, method = "silhouette") +
  labs(title = "Indice silhouette - Choix de K optimal")

# 3.2 K-means avec K=2, 3, 4
for (k in c(2, 3, 4)) {
  km <- kmeans(X_scaled, centers = k, nstart = 25, iter.max = 100)
  cat(paste0("\n--- K = ", k, " ---\n"))
  cat("Inertie totale:", round(km$tot.withinss, 2), "\n")
  cat("Taille des clusters:", table(km$cluster), "\n")
  df_temp <- df
  df_temp$cluster <- km$cluster
  cat("Profil moyen des clusters:\n")
  profil_tmp <- aggregate(. ~ cluster, data = df_temp[, c(vars, "perf", "cluster")], FUN = mean)
  profil_tmp[, -1] <- round(profil_tmp[, -1], 4)
  print(profil_tmp)
}

# 3.3 Clustering retenu : K=2
set.seed(42)
km_final <- kmeans(X_scaled, centers = 2, nstart = 25)
df$cluster <- as.factor(km_final$cluster)

cat("\n===== PROFILS DES CLUSTERS (K=2) =====\n")
profils_k2 <- aggregate(cbind(rendement, volatilite, volume, beta, dividende, perf) ~ cluster,
                        data = df, FUN = mean)
profils_k2[, -1] <- round(profils_k2[, -1], 4)
print(profils_k2)

# Visualisation clusters
fviz_cluster(km_final, data = X_scaled,
             palette = c("#E74C3C", "#2ECC71"),
             geom = "point",
             ellipse.type = "convex",
             ggtheme = theme_minimal(),
             main = "Clustering K-means (K=2) - Plan factoriel")

# ===========================================================
# PARTIE 4 : CLASSIFICATION
# ===========================================================

cat("\n===== CLASSIFICATION - RÉGRESSION LOGISTIQUE =====\n")

# 4.1 Préparation des données
df$perf <- as.factor(df$perf)
set.seed(42)
idx_train <- sample(1:nrow(df), size = 0.8 * nrow(df))
train <- df[idx_train, vars_all <- c(vars, "perf")]
test  <- df[-idx_train, vars_all]

# 4.2 Modèle de régression logistique
modele_logit <- glm(perf ~ rendement + volatilite + volume + beta + dividende,
                    data = train, family = binomial(link = "logit"))

cat("\n--- Résumé du modèle logistique ---\n")
summary(modele_logit)

cat("\n--- Odds ratios (exp des coefficients) ---\n")
print(round(exp(coef(modele_logit)), 4))

# 4.3 Prédictions sur le jeu de test
prob_pred <- predict(modele_logit, newdata = test, type = "response")
classe_pred <- ifelse(prob_pred > 0.5, 1, 0)

# 4.4 Matrice de confusion
cat("\n--- Matrice de confusion ---\n")
conf_mat <- table(Prédit = classe_pred, Réel = test$perf)
print(conf_mat)

# Métriques
accuracy  <- sum(diag(conf_mat)) / sum(conf_mat)
precision <- conf_mat[2,2] / sum(conf_mat[2,])
rappel    <- conf_mat[2,2] / sum(conf_mat[,2])
f1        <- 2 * precision * rappel / (precision + rappel)

cat("\n--- Métriques de performance ---\n")
cat("Accuracy  :", round(accuracy, 4), "\n")
cat("Précision :", round(precision, 4), "\n")
cat("Rappel    :", round(rappel, 4), "\n")
cat("F1-score  :", round(f1, 4), "\n")

# Courbe ROC
pred_rocr <- ROCR::prediction(prob_pred, test$perf)
perf_rocr <- ROCR::performance(pred_rocr, "tpr", "fpr")
auc_val   <- ROCR::performance(pred_rocr, "auc")@y.values[[1]]
plot(perf_rocr, colorize = TRUE, main = paste("Courbe ROC - AUC =", round(auc_val, 4)))
abline(0, 1, lty = 2)
cat("AUC :", round(auc_val, 4), "\n")

# ===========================================================
# PARTIE 5 : RÉGRESSION
# ===========================================================

cat("\n===== RÉGRESSION LINÉAIRE (variable cible : rendement) =====\n")

df$perf <- as.numeric(as.character(df$perf))

# 5.1 Modèle de régression
modele_reg <- lm(rendement ~ volatilite + volume + beta + dividende, data = df)

cat("\n--- Résumé du modèle de régression ---\n")
summary(modele_reg)

cat("\n--- Intervalles de confiance (95%) ---\n")
print(round(confint(modele_reg), 6))

# 5.2 Analyse des résidus
par(mfrow = c(2, 2))
plot(modele_reg, main = "Analyse des résidus")
par(mfrow = c(1, 1))

# Test de normalité des résidus
cat("\n--- Test de Shapiro-Wilk (normalité des résidus) ---\n")
shapiro.test(residuals(modele_reg))

# Test d'hétéroscédasticité
cat("\n--- Test de Breusch-Pagan (hétéroscédasticité) ---\n")
library(lmtest)
bptest(modele_reg)

# VIF (multicolinéarité)
cat("\n--- VIF (facteurs d'inflation de la variance) ---\n")
print(round(vif(modele_reg), 3))

# ===========================================================
# PARTIE 6 : DISCUSSION
# ===========================================================

cat("\n")
cat("==========================================================\n")
cat("SYNTHÈSE : DONNÉES FINANCIÈRES\n")
cat("==========================================================\n")
cat("\n1. L'ACP révèle que les données financières sont peu structurées :\n")
cat("   - Aucun axe ne domine clairement (variance max ~33%)\n")
cat("   - Axe 1 : opposition volatilité/béta vs rendement\n")
cat("   - Axe 2 : rendement vs dividende\n")
cat("\n2. Le clustering identifie 2 profils d'actifs :\n")
cat("   - Cluster 1 : haute volatilité, béta élevé (actifs risqués)\n")
cat("   - Cluster 2 : faible volatilité, volume élevé (actifs stables)\n")
cat("\n3. La régression logistique prédit parfaitement perf (Accuracy=1.0)\n")
cat("   → Probable lien quasi-déterministe avec le rendement\n")
cat("\n4. La régression linéaire sur le rendement est non significative (R²~0.006)\n")
cat("   → Confirme le bruit inhérent aux données financières\n")
cat("\n5. Limites : données simulées, absence de dimension temporelle,\n")
cat("   relations non linéaires non capturées.\n")