#############################################
# 1) GÉNÉRATION DES DONNÉES
#############################################

set.seed(123)

n <- 400

# Structure latente 1D
latent <- rnorm(n)

# Variables corrélées
X1 <- latent + rnorm(n, 0, 0.3)
X2 <- 2*latent + rnorm(n, 0, 0.3)
X3 <- -latent + rnorm(n, 0, 0.3)

# Bruit pur
X4 <- rnorm(n)

# Groupe latent
group <- ifelse(latent > 0, "A", "B")

data <- data.frame(X1, X2, X3, X4, group)

#############################################
# 2) ACP
#############################################

pca <- prcomp(data[,1:4], scale. = TRUE)

summary(pca)

#############################################
# 3) VARIANCE EXPLIQUÉE
#############################################

eig <- pca$sdev^2
prop_var <- eig / sum(eig)

plot(prop_var,
     type = "b",
     xlab = "Composante",
     ylab = "Proportion variance",
     main = "Scree Plot")

#############################################
# 4) INTERPRÉTATION DES AXES
#############################################

print("Loadings :")
print(pca$rotation)

#############################################
# 5) CONTRIBUTIONS DES VARIABLES
#############################################

loadings <- pca$rotation
ctr_var <- loadings^2
print("Contributions variables :")
print(ctr_var)

#############################################
# 6) CONTRIBUTIONS DES INDIVIDUS
#############################################

coord <- pca$x
eig <- pca$sdev^2

ctr_ind <- sweep(coord^2, 2, eig, FUN="/")
ctr_ind <- ctr_ind / nrow(data)

print("Contributions individus (head) :")
print(head(ctr_ind))

#############################################
# 7) QUALITÉ DE REPRÉSENTATION (cos²)
#############################################

cos2_ind <- coord^2 / rowSums(coord^2)

print("cos² individus (head) :")
print(head(cos2_ind))

#############################################
# 8) VISUALISATION ACP
#############################################

plot(coord[,1], coord[,2],
     col = as.factor(group),
     pch = 19,
     xlab = "PC1",
     ylab = "PC2",
     main = "Projection ACP")

legend("topright",
       legend = levels(as.factor(group)),
       col = 1:2,
       pch = 19)

#############################################
# 9) EFFET D'UN OUTLIER
#############################################

data_out <- rbind(data,
                  c(10,10,10,10,"A"))
# convertir les 4 premières colonnes en numérique
data_out[,1:4] <- lapply(data_out[,1:4], as.numeric)

pca_out <- prcomp(data_out[,1:4], scale. = TRUE)

print("Variance expliquée avec outlier :")
print(summary(pca_out))

#############################################
# 10) t-SNE
#############################################

if(!require(Rtsne)) install.packages("Rtsne")
library(Rtsne)

tsne_res <- Rtsne(scale(data[,1:4]), dims = 2)

plot(tsne_res$Y,
     col = as.factor(group),
     pch = 19,
     main = "Projection t-SNE")

#############################################
# 11) UMAP
#############################################

if(!require(umap)) install.packages("umap")
library(umap)

umap_res <- umap(scale(data[,1:4]))

plot(umap_res$layout,
     col = as.factor(group),
     pch = 19,
     main = "Projection UMAP")

