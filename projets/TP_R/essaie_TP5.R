#############################################
# TP MODULE 5 — CLUSTERING
#############################################

#############################################
# 1) Chargement des données
#############################################

data(iris)

head(iris)
summary(iris)

#############################################
# 2) Visualisation des données
#############################################

pairs(iris[,1:4],
      col = iris$Species,
      pch = 19,
      main = "Relations entre variables")

#############################################
# 3) Prétraitement : standardisation
#############################################

iris_num <- iris[,1:4]

iris_scaled <- scale(iris_num)

#############################################
# 4) K-means clustering
#############################################

set.seed(123)

kmeans_res <- kmeans(iris_scaled, centers = 3)

kmeans_res

#############################################
# 5) Visualisation des clusters
#############################################

plot(iris_scaled[,1], iris_scaled[,2],
     col = kmeans_res$cluster,
     pch = 19,
     xlab = "Sepal Length",
     ylab = "Sepal Width",
     main = "Clusters K-means")

#############################################
# 6) Centres des clusters
#############################################

print("Centres des clusters :")
print(kmeans_res$centers)

#############################################
# 7) Comparaison avec les vraies espèces
#############################################

table(Clusters = kmeans_res$cluster,
      Species = iris$Species)

#############################################
# 8) Méthode du coude
#############################################

wss <- numeric(10)

for(i in 1:10){
  
  km <- kmeans(iris_scaled, centers = i)
  
  wss[i] <- km$tot.withinss
}

plot(1:10, wss,
     type = "b",
     pch = 19,
     xlab = "Nombre de clusters",
     ylab = "Inertie intra-cluster",
     main = "Méthode du coude")

#############################################
# 9) Silhouette
#############################################

if(!require(cluster)) install.packages("cluster")

library(cluster)

sil <- silhouette(kmeans_res$cluster,
                  dist(iris_scaled))

plot(sil,
     main = "Coefficient de silhouette")

#############################################
# 10) Clustering hiérarchique
#############################################

dist_mat <- dist(iris_scaled)

hc <- hclust(dist_mat)

plot(hc,
     main = "Dendrogramme")

#############################################
# 11) Découpage de l'arbre
#############################################

clusters_hc <- cutree(hc, k = 3)

plot(iris_scaled[,1], iris_scaled[,2],
     col = clusters_hc,
     pch = 19,
     main = "Clusters hiérarchiques")

#############################################
# 12) Comparaison K-means vs hiérarchique
#############################################

table(Kmeans = kmeans_res$cluster,
      Hierarchical = clusters_hc)