package SharedKernel;

public interface EventHandler<T extends DomainEvent> {
    void handle(T event);
}
