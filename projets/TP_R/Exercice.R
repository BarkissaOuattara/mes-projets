#Données
A<-c(26,27,35,36,38,38,41,42,45,50,65)

B<-c(29,42,44,44,45,48,48,52,56, 56, 58, 58, 60, 61,63 ,63,69 )

C<-c(26, 26, 30, 30,33, 36, 38, 38,39, 46, 47, 51,51, 56, 75)

#temps
temps<-c(A,B,C)

#TRAITEMENT
traitement<-factor(c(rep("A", length(A)), rep("B", length(B)), rep("C", length(C))))
#creation du tableau de traitement

data<-data.frame(traitement,temps)

#Afficher les premières ligne
head(data)

#Resumé de data
summary(data)

#Moyennes par traitement
tapply(data$temps, data$traitement, mean)
#Variance par traitement
tapply(data$temps, data$traitement, var)
#Ecart type par traitement
tapply(data$temps, data$traitement, sd)

#Effectif par traitement
tapply(data$temps, data$traitement, length)

#Tableau recapitulatif des moyennes
aggregate(temps ~ traitement, data=data, mean)

#boite a moustache
boxplot(temps ~ traitement, data=data, col="lightblue",main="comparaison des traitements", xlab="traitement", ylab="temps avant la prochaine crise")

#Graphique des points individuels
stripchart(temps ~ traitement, data=data, vertical=TRUE, method="jitter", pch=19, col="darkblue", main="Observations individuelles")

#6-Estimation du modele
model <- aov(temps ~ traitement, data=data)

#Afficher le tableau de l'ANOVA
summary(model)

#7-Verification des hypotheses de l'ANOVA
#Examination des residus
residus <- residuals(model)

#7-1. Normalite des residus

#QQ plot
qqnorm(residus)
qqline(residus, col="red", main="Histogramme des residus")

#Boxplot des residus
boxplot(residus,col="lightgray", main="Boxplot des residus")

#Histogramme des residus
hist(residus,col="lightgray", main="Histogramme des residus")

#Test de Shapiro-Wilk pour la normalite
shapiro.test(residus)

#Test de Bartlett pour l'homogeneite des variances
bartlett.test(temps ~ traitement, data=data)

#Test de Levene pour l'homogeneite des variances
library(car)
leveneTest(temps ~ traitement, data=data)

#8- calcul de la taille de l'effet

#somme des carrées expliqué
SCE<- anova(model)[1,2]

#somme des carrées totaux
SCT<- sum((temps - mean(temps))^2)
#coefficient des carree
eta2=SCE/SCT
eta2
#9- comparaison multiples(post-hot)

#test de Tukey
TukeyHSD(model)

#Graphique des intervalles de confiance pour les comparaisons multiples
plot(TukeyHSD(model))

#10- Moyennes estimées

model.tables(model, types="means")

#11- Graphique des moyenns

means <- tapply(data$temps, data$traitement, mean)
plot(means, type="b", 
     col="red", pch=19,
     main="Moyennes par traitement",
     xlab="Traitement", 
     ylab="Temps moyen avant la prochaine crise")
  