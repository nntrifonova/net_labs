package Server;

public interface ByteCounter {
    long startNewLap();
    void registerBytes(long numberBytes);
    long getTotalBytesNumber();
}
