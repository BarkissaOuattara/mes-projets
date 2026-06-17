# =========================================================
# PROJET : ANALYSE DES DONNEES FINANCIERES
# Partie 3.1 : Exploration
# =========================================================

# 1) Importation des données
data <- read.csv("~/Desktop/TP_R/data_finance.csv")
# Vérification rapide
str(data)
head(data)
summary(data)

# =========================================================
# QUESTION 1 : Décrire les distributions des variables
# =========================================================

# Sélection des variables numériques
vars_num <- data[, sapply(data, is.numeric)]

# Calcul des statistiques descriptives
resume_stat <- data.frame(
  Variable = names(vars_num),
  Moyenne = sapply(vars_num, mean, na.rm = TRUE),
  Ecart_type = sapply(vars_num, sd, na.rm = TRUE),
  Min = sapply(vars_num, min, na.rm = TRUE),
  Q1 = sapply(vars_num, quantile, probs = 0.25, na.rm = TRUE),
  Mediane = sapply(vars_num, median, na.rm = TRUE),
  Q3 = sapply(vars_num, quantile, probs = 0.75, na.rm = TRUE),
  Max = sapply(vars_num, max, na.rm = TRUE)
)

# Affichage du tableau
print(resume_stat)

# Arrondir pour une lecture plus propre
resume_stat_arrondi <- resume_stat
resume_stat_arrondi[,-1] <- round(resume_stat_arrondi[,-1], 4)

cat("\n=============================\n")
cat("RESUME STATISTIQUE DES VARIABLES\n")
cat("=============================\n")
print(resume_stat_arrondi)

# Histogrammes pour visualiser les distributions
par(mfrow = c(2, 3))  # disposition des graphiques

for (v in names(vars_num)) {
  hist(vars_num[[v]],
       main = paste("Histogramme de", v),
       xlab = v,
       ylab = "Fréquence",
       col = "lightgray",
       border = "black")
}

# Remettre l'affichage graphique normal
par(mfrow = c(1, 1))

# Boxplots pour visualiser la dispersion et les valeurs atypiques
par(mfrow = c(2, 3))

for (v in names(vars_num)) {
  boxplot(vars_num[[v]],
          main = paste("Boxplot de", v),
          ylab = v,
          col = "lightgray")
}

par(mfrow = c(1, 1))

# =========================================================
# QUESTION 2 : Identifier les variables les plus instables
# =========================================================

# On exclut la variable binaire "perf" de l'analyse du coefficient de variation
vars_cont <- subset(vars_num, select = -perf)

# Calcul du coefficient de variation : CV = ecart-type / moyenne
cv <- sapply(vars_cont, function(x) sd(x, na.rm = TRUE) / mean(x, na.rm = TRUE))

# Mise en tableau
tableau_cv <- data.frame(
  Variable = names(cv),
  Coefficient_variation = round(cv, 3)
)

# Tri décroissant pour identifier les plus instables
tableau_cv <- tableau_cv[order(-tableau_cv$Coefficient_variation), ]

cat("\n=============================\n")
cat("COEFFICIENTS DE VARIATION\n")
cat("=============================\n")
print(tableau_cv)

# Variable la plus instable
cat("\nLa variable la plus instable est :", tableau_cv$Variable[1], "\n")

# Barplot des coefficients de variation
barplot(tableau_cv$Coefficient_variation,
        names.arg = tableau_cv$Variable,
        main = "Coefficient de variation des variables",
        ylab = "CV",
        col = "lightgray",
        border = "black")

# =========================================================
# MATRICE DE CORRELATION
# =========================================================

# Variables continues seulement
vars_cont <- subset(data, select = -perf)

# Matrice de corrélation
cor_mat <- cor(vars_cont)

cat("\n=============================\n")
cat("MATRICE DE CORRELATION\n")
cat("=============================\n")
print(round(cor_mat, 3))

heatmap(cor_mat,
        main = "Matrice de corrélation",
        col = heat.colors(20),
        scale = "none")
# =========================================================
# 3.2 ACP
# 1. Réaliser une ACP
# =========================================================

# Sélection des variables quantitatives pour l'ACP
# On retire perf car c'est une variable binaire
data_acp <- subset(data, select = -perf)

# Vérification
str(data_acp)
summary(data_acp)

# Réalisation de l'ACP
# scale. = TRUE car les variables n'ont pas la même échelle
acp <- prcomp(data_acp, center = TRUE, scale. = TRUE)

# Résumé de l'ACP
summary(acp)

# Valeurs propres
eig_val <- acp$sdev^2
print(eig_val)

# Pourcentage de variance expliquée
variance_expliquee <- eig_val / sum(eig_val) * 100
print(round(variance_expliquee, 2))

# Pourcentage cumulé
variance_cumulee <- cumsum(variance_expliquee)
print(round(variance_cumulee, 2))

# Tableau récapitulatif
tableau_acp <- data.frame(
  Axe = paste0("Dim", 1:length(eig_val)),
  Valeur_propre = round(eig_val, 4),
  Variance_pourcent = round(variance_expliquee, 2),
  Variance_cumulee = round(variance_cumulee, 2)
)

cat("\n=============================\n")
cat("TABLEAU DES VALEURS PROPRES\n")
cat("=============================\n")
print(tableau_acp)

# Cercle des corrélations
plot(acp, type = "l", main = "Eboulis des valeurs propres")

# Projection des individus
biplot(acp, scale = 0)

# Coordonnées des variables sur les axes
coord_var <- acp$rotation
print(round(coord_var, 4))

# Coordonnées des individus sur les axes
coord_ind <- acp$x
head(round(coord_ind, 4))


# =========================================================
# 3.2 ACP
# 2. Interpréter les axes
# =========================================================

# Coordonnées des variables (loadings)
coord_var <- acp$rotation
cat("\n=============================\n")
cat("COORDONNEES DES VARIABLES SUR LES AXES\n")
cat("=============================\n")
print(round(coord_var, 4))

# Coordonnées des variables sur les deux premiers axes
coord_var_12 <- round(coord_var[, 1:2], 4)
cat("\n=============================\n")
cat("VARIABLES SUR DIM 1 ET DIM 2\n")
cat("=============================\n")
print(coord_var_12)

# Qualité de représentation des variables sur les axes
# cos² = corrélation² entre variable et axe
coord_cor <- sweep(coord_var, 2, acp$sdev, FUN = "*")
cos2_var <- coord_cor^2

cat("\n=============================\n")
cat("COS² DES VARIABLES\n")
cat("=============================\n")
print(round(cos2_var[, 1:2], 4))

# Contribution des variables aux axes
# contribution_jk = (corr_jk^2 / valeur_propre_k) * 100
eig_val <- acp$sdev^2
contrib_var <- sweep(coord_cor^2, 2, eig_val, FUN = "/") * 100

cat("\n=============================\n")
cat("CONTRIBUTIONS DES VARIABLES AUX AXES (%)\n")
cat("=============================\n")
print(round(contrib_var[, 1:2], 2))

# Variables les plus importantes sur chaque axe
cat("\n=============================\n")
cat("VARIABLES LES PLUS IMPORTANTES SUR DIM 1\n")
cat("=============================\n")
print(sort(abs(coord_var[,1]), decreasing = TRUE))

cat("\n=============================\n")
cat("VARIABLES LES PLUS IMPORTANTES SUR DIM 2\n")
cat("=============================\n")
print(sort(abs(coord_var[,2]), decreasing = TRUE))

# Cercle des corrélations avec noms propres
plot(coord_cor[,1], coord_cor[,2],
     type = "n",
     xlim = c(-1, 1), ylim = c(-1, 1),
     xlab = paste0("Dim 1 (", round(variance_expliquee[1], 2), " %)"),
     ylab = paste0("Dim 2 (", round(variance_expliquee[2], 2), " %)"),
     main = "Cercle des corrélations",
     asp = 1)

theta <- seq(0, 2*pi, length = 200)
lines(cos(theta), sin(theta), col = "black")
abline(h = 0, v = 0, lty = 2, col = "gray")

arrows(0, 0, coord_cor[,1], coord_cor[,2],
       length = 0.1, angle = 20, col = "blue")

text(coord_cor[,1], coord_cor[,2],
     labels = rownames(coord_cor),
     pos = 4, cex = 0.9, col = "red")

# =========================================================
# 3.2 CLUSTERING
# =========================================================

# Données ACP (on utilise les coordonnées des individus)
data_clust <- acp$x[,1:2]  # Dim1 et Dim2

# Nombre de clusters (choix = 3 ici)
set.seed(123)
kmeans_res <- kmeans(data_clust, centers = 3, nstart = 25)

# Résultat
print(kmeans_res)

# Ajout des clusters dans les données
data$cluster <- as.factor(kmeans_res$cluster)

# Visualisation
plot(data_clust,
     col = kmeans_res$cluster,
     pch = 19,
     xlab = "Dim 1",
     ylab = "Dim 2",
     main = "Clustering des actifs (K-means)")
grid()

# Centres des clusters
centres <- kmeans_res$centers
print(centres)

# Taille des clusters
table_clusters <- table(data$cluster)
print(table_clusters)

# =========================================================
# =========================================================
# 3.3 CLUSTERING
# 2. Interpréter économiquement les clusters
# =========================================================

# Vérification des effectifs
effectifs_clusters <- table(data$cluster)

cat("\n=============================\n")
cat("EFFECTIFS DES CLUSTERS\n")
cat("=============================\n")
print(effectifs_clusters)

# Centres des clusters dans l'espace factoriel
centres <- kmeans_res$centers

cat("\n=============================\n")
cat("CENTRES DES CLUSTERS (DIM 1 ET DIM 2)\n")
cat("=============================\n")
print(round(centres, 4))

# Moyennes des variables d'origine par cluster
profil_clusters <- aggregate(
  data[, c("rendement", "volatilite", "volume", "beta", "dividende", "perf")],
  by = list(Cluster = data$cluster),
  FUN = mean
)

# Arrondi propre : on ne touche pas à la colonne Cluster
profil_clusters[, -1] <- round(profil_clusters[, -1], 4)

cat("\n=============================\n")
cat("PROFIL ECONOMIQUE DES CLUSTERS\n")
cat("=============================\n")
print(profil_clusters)

# Tableau de contingence cluster / perf
tab_cluster_perf <- table(data$cluster, data$perf)

cat("\n=============================\n")
cat("TABLE DE CONTINGENCE CLUSTER / PERF\n")
cat("=============================\n")
print(tab_cluster_perf)

# Proportions par cluster
prop_cluster_perf <- prop.table(tab_cluster_perf, margin = 1)

cat("\n=============================\n")
cat("PROPORTIONS DE PERF PAR CLUSTER\n")
cat("=============================\n")
print(round(prop_cluster_perf, 4))

# =========================================================
# Visualisation : boxplots par cluster
# =========================================================

par(mfrow = c(2, 3))

boxplot(rendement ~ cluster, data = data,
        main = "Rendement par cluster",
        xlab = "Cluster", ylab = "Rendement",
        col = "lightgray")

boxplot(volatilite ~ cluster, data = data,
        main = "Volatilité par cluster",
        xlab = "Cluster", ylab = "Volatilité",
        col = "lightgray")

boxplot(volume ~ cluster, data = data,
        main = "Volume par cluster",
        xlab = "Cluster", ylab = "Volume",
        col = "lightgray")

boxplot(beta ~ cluster, data = data,
        main = "Bêta par cluster",
        xlab = "Cluster", ylab = "Bêta",
        col = "lightgray")

boxplot(dividende ~ cluster, data = data,
        main = "Dividende par cluster",
        xlab = "Cluster", ylab = "Dividende",
        col = "lightgray")

boxplot(perf ~ cluster, data = data,
        main = "Performance par cluster",
        xlab = "Cluster", ylab = "Perf",
        col = "lightgray")

par(mfrow = c(1, 1))

# =========================================================
# 3.4 CLASSIFICATION
# =========================================================

# La variable cible doit être un facteur pour une classification
data$perf <- as.factor(data$perf)

# ---------------------------------------------------------
# 1. Modèle logistique
# ---------------------------------------------------------
modele_logit <- glm(
  perf ~ rendement + volatilite + volume + beta + dividende,
  data = data,
  family = binomial(link = "logit")
)

cat("\n=============================\n")
cat("MODELE LOGISTIQUE\n")
cat("=============================\n")
print(summary(modele_logit))

# Vérification simple de convergence
cat("\nConvergence du modèle :", modele_logit$converged, "\n")

if (!modele_logit$converged) {
  cat("Attention : le modèle n'a pas convergé correctement.\n")
}

# ---------------------------------------------------------
# 2. Probabilités prédites
# ---------------------------------------------------------
proba_pred <- predict(modele_logit, type = "response")

cat("\n=============================\n")
cat("RESUME DES PROBABILITES PREDITES\n")
cat("=============================\n")
print(summary(proba_pred))

# ---------------------------------------------------------
# 3. Classification (seuil = 0.5)
# ---------------------------------------------------------
pred_class <- ifelse(proba_pred > 0.5, 1, 0)
pred_class <- factor(pred_class, levels = c(0, 1))
data$perf <- factor(data$perf, levels = c(0, 1))

# ---------------------------------------------------------
# 4. Matrice de confusion
# ---------------------------------------------------------
mat_conf <- table(Prediction = pred_class, Reel = data$perf)

cat("\n=============================\n")
cat("MATRICE DE CONFUSION\n")
cat("=============================\n")
print(mat_conf)

# ---------------------------------------------------------
# 5. Mesures de qualité
# ---------------------------------------------------------
accuracy <- sum(diag(mat_conf)) / sum(mat_conf)
erreur <- 1 - accuracy

# Sensibilité = vrais positifs / positifs réels
# Ici, on prend la classe 1 comme classe positive
sensitivity <- if ((mat_conf["1", "1"] + mat_conf["0", "1"]) > 0) {
  mat_conf["1", "1"] / (mat_conf["1", "1"] + mat_conf["0", "1"])
} else {
  NA
}

# Spécificité = vrais négatifs / négatifs réels
specificity <- if ((mat_conf["0", "0"] + mat_conf["1", "0"]) > 0) {
  mat_conf["0", "0"] / (mat_conf["0", "0"] + mat_conf["1", "0"])
} else {
  NA
}

cat("\n=============================\n")
cat("MESURES DE PERFORMANCE\n")
cat("=============================\n")
cat("Accuracy       :", round(accuracy, 4), "\n")
cat("Taux d'erreur  :", round(erreur, 4), "\n")
cat("Sensibilite    :", round(sensitivity, 4), "\n")
cat("Specificite    :", round(specificity, 4), "\n")

modele_logit_simple <- glm(
  perf ~ rendement + volatilite + beta + dividende,
  data = data,
  family = binomial(link = "logit")
)

print(summary(modele_logit_simple))

