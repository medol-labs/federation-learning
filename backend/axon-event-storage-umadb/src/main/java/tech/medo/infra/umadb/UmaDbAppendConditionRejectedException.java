package tech.medo.infra.umadb;

public final class UmaDbAppendConditionRejectedException extends RuntimeException {
    public UmaDbAppendConditionRejectedException(Throwable cause) {
        super("UmaDB append condition rejected the event batch", cause);
    }
}
