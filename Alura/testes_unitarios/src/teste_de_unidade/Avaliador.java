package teste_de_unidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Avaliador {

    private double maiorDeTodos = Double.NEGATIVE_INFINITY;
    private double menorDeTodos = Double.POSITIVE_INFINITY;
    private double valorMedio = 0;
    private List<Double> lances;
    private List<Lance> maiores;

    public void avalia(Leilao leilao) {
        if (leilao.getLances().isEmpty()) {
            throw new RuntimeException("Não é possível avaliar um leilão sem lances");
        }

        for (Lance lance : leilao.getLances()) {
            if (lance.getValor() > maiorDeTodos) {
                maiorDeTodos = lance.getValor();
            }
            if (lance.getValor() < menorDeTodos) {
                menorDeTodos = lance.getValor();
            }
        }

        pegaOsMaioresNo(leilao);
    }

    public double avaliador(Leilao leilao) {

        for (Lance lance : leilao.getLances()) {
            valorMedio = valorMedio + lance.getValor();
        }

        valorMedio = valorMedio / leilao.getLances().size();

        return valorMedio;
    }

    public List<Double> armazenarLances(Leilao leilao) {
        lances = new ArrayList<>();

        for (Lance lance : leilao.getLances()) {
            lances.add(lance.getValor());
        }

        return lances;
    }

    private void pegaOsMaioresNo(Leilao leilao) {
        maiores = new ArrayList<Lance>(leilao.getLances());
        Collections.sort(maiores, new Comparator<Lance>() {
            public int compare(Lance o1, Lance o2) {
                if (o1.getValor() < o2.getValor()) {
                    return 1;
                }
                if (o1.getValor() > o2.getValor()) {
                    return -1;
                }
                return 0;
            }
        });
        maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
    }

    public List<Lance> getTresMaiores() {
        return this.maiores;
    }

    public double getMaiorLance() {
        return maiorDeTodos;
    }

    public double getMenorLance() {
        return menorDeTodos;
    }

    public double getvalorMedio() {
        return valorMedio;
    }
}
