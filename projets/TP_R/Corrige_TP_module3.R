#########################################
# Corrige_TP_module3.R ###############
#################################
# 1. importation des données
data <- read.csv("~/Desktop/TP_R/data_module3.csv")
# 2. Statistiques descriptives avancées
summary(data)

# Médiane
median(data$score)

# IQR
IQR(data$score)

# Skewness et Kurtosis
library(moments)
skewness(data$score)
kurtosis(data$score)
# 3. visualisation avancée
library(ggplot2)
 boxplot(score ~ filiere, data = data) +
   labs(title = "Boxplot du score par filière", x = "Filière", y = "Score")
# Questions
 
# La distribution est-elle symétrique ?
   
#   Les moments d’ordre 1 et 2 suffisent-ils ? 

# 4. Histogramme intelligent
 hist(data$score,
      breaks = "FD",   # Freedman–Diaconis
      col = "lightblue",
      main = "Histogramme du score",
      xlab = "Score")
 # Comparer avec :
 hist(data$score, breaks = 5)
 hist(data$score, breaks = 40)
# Que change le nombre de classes ?
# Quelle version est la plus informative ? 

# 5. Boxplot 
 boxplot(score ~ filiere,
         data = data,
         col = c("orange","lightgreen","lightblue"),
         main = "Score par filière")
# Observe-t-on des différences ?
#  Y a-t-il des valeurs extrêmes ?

############################################
# Partie 3 — Analyse bivariée #############
##########################################
 # 1. Scatter plot
 plot(data$heures_etude, data$score,
      col = rgb(0,0,1,0.4),
      pch = 19,
      main = "Score vs Heures d'étude",
      xlab = "Heures d'étude",
      ylab = "Score")
 # Ajouter une droite de régression :
 abline(lm(score ~ heures_etude, data=data),
        col="red", lwd=2)
# La relation semble-t-elle linéaire ?
# Observe-t-on une courbure ?
# 2. Corrélation
 cor(data$heures_etude, data$score)
 # Comparer avec corrélation de Spearman :
 cor(data$heures_etude, data$score, method="spearman")
# 
 # Discussion
# Une corrélation élevée garantit-elle linéarité ?
# Peut-on détecter la non-linéarité ici ?

# 3. Heatmap de corrélation
 library(corrplot)
 
 num_data <- data[, c("age","heures_etude","score")]
 corr_matrix <- cor(num_data)
 
 corrplot(corr_matrix,
          method="color",
          type="upper",
          addCoef.col="black")
# Questions
# Quelles variables semblent redondantes ?
#   Une corrélation faible exclut-elle toute dépendance ? 
 
###########################################
# Partie 4 — Dépendance non linéaire #####
##########################################
# Créer variable quadratique :
 data$heures2 <- data$heures_etude^2
 cor(data$heures_etude, data$heures2)
# Tracer
 plot(data$heures_etude, data$heures2,
      main="Relation non linéaire")
# Discussion Discussion
# Corrélation proche de 0 ?
# Dépendance pourtant évidente ? 

###########################################
# Partie 5 — Données d’Anscombe  ######
########################################
 data(anscombe)
 
 par(mfrow=c(2,2))
 
 for(i in 1:4){
   plot(anscombe[,i],
        anscombe[,i+4],
        main=paste("Set",i),
        pch=19)
   abline(lm(anscombe[,i+4] ~ anscombe[,i]),
          col="red")
 }
# Questions
# Les statistiques numériques sont-elles identiques ?
# Les formes sont-elles identiques ?
# Quelle leçon méthodologique ?

####################################################
#  Partie 6 — Ce que les graphes cachent #####
###################################################
# Densité cachée
 library(hexbin)
 plot(hexbin(data$heures_etude, data$score),
      main="Hexbin plot")
# Discussion
# Que révèle hexbin que scatter simple ne montre pas ?
# Pourquoi la transparence est utile ?
 