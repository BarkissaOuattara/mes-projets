import numpy as np
import sympy as sp

class AlgoGradientPasFixe:
    def __init__(self, func_str, alpha=0.01, max_iter=1000, tol=1e-6):
        """
        Algorithme du gradient à pas fixe pour minimiser une fonction saisie par l'utilisateur.

        :param func_str: Chaîne de caractères représentant la fonction (ex: "x**2 + y**2").
        :param alpha: Pas d'apprentissage (fixe).
        :param max_iter: Nombre maximal d'itérations.
        :param tol: Tolérance pour le critère d'arrêt.
        """
        self.alpha = alpha
        self.max_iter = max_iter
        self.tol = tol
        
        # Définition des variables symboliques
        self.variables = sp.symbols('x y z')
        self.func = sp.sympify(func_str)  # Convertit la chaîne en fonction mathématique
        self.grad = [sp.diff(self.func, var) for var in self.variables]  # Calcul du gradient
        self.grad_func = sp.lambdify(self.variables, self.grad, 'numpy')  # Convertit en fonction Python

    def optimize(self, x0):
        """
        Effectue l'optimisation à partir d'un point initial.

        :param x0: Point initial sous forme de liste [x, y, z] (ou moins si la fonction a <3 variables).
        :return: Solution optimisée et historique des points.
        """
        x = np.array(x0, dtype=float)
        history = [x.copy()]

        for _ in range(self.max_iter):
            grad = np.array(self.grad_func(*x))  # Calcul du gradient
            if np.linalg.norm(grad) < self.tol:
                break
            x -= self.alpha * grad  # Mise à jour selon la descente de gradient
            history.append(x.copy())

        return x, history

# ---- 🚀 DEMANDE À L'UTILISATEUR ----
func_str = input("Entrez la fonction à minimiser (ex: 'x**2 + y**2 + z**2'): ")
algo = AlgoGradientPasFixe(func_str, alpha=0.1)

# Point de départ demandé à l'utilisateur
x0 = list(map(float, input("Entrez les valeurs initiales (ex: '1 1 1' pour x=1, y=1, z=1) : ").split()))

# Exécution de l'algorithme
x_opt, traj = algo.optimize(x0)

print("Solution trouvée :", x_opt)
