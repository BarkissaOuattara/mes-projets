import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AlgoGradientPasFixe {
    
    public static double fonction(Map<String, Double> variables, Map<String, Double> coefficients) {
        double result = 0.0;
        for (Map.Entry<String, Double> entry : variables.entrySet()) {
            String var = entry.getKey();
            double value = entry.getValue();
            if (coefficients.containsKey(var)) {
                result += coefficients.get(var) * value * value;
            }
        }
        return result;
    }
    
    public static Map<String, Double> gradient(Map<String, Double> variables, Map<String, Double> coefficients) {
        Map<String, Double> grad = new HashMap<>();
        for (Map.Entry<String, Double> entry : variables.entrySet()) {
            String var = entry.getKey();
            double value = entry.getValue();
            if (coefficients.containsKey(var)) {
                grad.put(var, 2 * coefficients.get(var) * value);
            }
        }
        return grad;
    }
    
    public static Map<String, Double> gradientDescent(Map<String, Double> variables, double alpha, int iterations, Map<String, Double> coefficients) {
        for (int i = 0; i < iterations; i++) {
            Map<String, Double> grad = gradient(variables, coefficients);
            for (String var : variables.keySet()) {
                variables.put(var, variables.get(var) - alpha * grad.get(var));
            }
        }
        return variables;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Combien de variables y a-t-il ? (max 3)");
        int numVars = scanner.nextInt();
        
        Map<String, Double> variables = new HashMap<>();
        Map<String, Double> coefficients = new HashMap<>();
        
        for (int i = 0; i < numVars; i++) {
            System.out.println("Entrez le nom de la variable " + (i + 1) + " :");
            String varName = scanner.next();
            
            System.out.println("Entrez le coefficient de " + varName + "^2 :");
            double coeff = scanner.nextDouble();
            coefficients.put(varName, coeff);
            
            System.out.println("Entrez la valeur initiale de " + varName + " :");
            double initialValue = scanner.nextDouble();
            variables.put(varName, initialValue);
        }
        
        System.out.println("Entrez le pas fixe alpha :");
        double alpha = scanner.nextDouble();
        
        System.out.println("Entrez le nombre d'itérations :");
        int iterations = scanner.nextInt();
        
        Map<String, Double> minimum = gradientDescent(variables, alpha, iterations, coefficients);
        
        System.out.println("Minimum trouvé : " + minimum);
        
        scanner.close();
    }
}
