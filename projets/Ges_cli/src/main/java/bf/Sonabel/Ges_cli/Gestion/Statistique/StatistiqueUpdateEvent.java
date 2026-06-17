package bf.Sonabel.Ges_cli.Gestion.Statistique;

public class StatistiqueUpdateEvent {
    private final Object source;

    public StatistiqueUpdateEvent(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return source;
    }
}
