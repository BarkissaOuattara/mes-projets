#Saisie les variable
X=c(2,3,4,5,5,6,6,7,8,8);
Y=c(3,3,2,4,5,3,4,4,5,6);
#Parametre de position 
#Moyenne 
mean(X); mean(Y);
#Variance
var(X); var(Y);
sd(X); sd(Y);
cov(X,Y);
cor(X,Y);
#nuage de points
plot(Y ~ X, pch=19,col=rgb(1,0,0,0.5),
     xlab = "X", ylab = "Y", main = "nuage de point")
model <- lm(Y ~ X)
summary(model)

#La droite de regression
abline(model, col="blue", lwd=2)

#tes de normalite
shapiro.test(residuals(model))

             