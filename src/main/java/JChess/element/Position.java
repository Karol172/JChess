package JChess.element;

public class Position implements Comparable<Position>{
    private final Integer file;
    private final Integer rank;

    public Position(String name) {
        if (name.length() == 2 && isInRange(name.toUpperCase().charAt(0), name.charAt(1),
                'A', 'H', '1', '8')) {
            this.file = getFileFromName(name);
            this.rank = getRankFromName(name);
        }
        else
            throw new IllegalArgumentException("Position have to consist of one character A-H and one digit 1-8.");
    }

    public Position(int file, int rank) {
        if (isInRange(file, rank,1, 8,  1,  8)) {
            this.file = file;
            this.rank = rank;
        }
        else
            throw new IllegalArgumentException("Position have to consist of one character A-H and one digit 1-8.");
    }

    public Integer getFile() {
        return file;
    }

    public Integer getRank() { return rank; }

    public String getName() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append((char)('A' + this.file - 1));
        stringBuffer.append(Integer.valueOf(this.rank).toString());
        return stringBuffer.toString();
    }

    public Integer getDistanceX (Position position) {
        if (position != null)
            return this.getFile() - position.getFile();
        return null;
    }

    public Integer getDistanceY (Position position) {
        if (position != null)
            return this.getRank() - position.getRank();
        return null;
    }

    private int getRankFromName (String name) { return name.toUpperCase().charAt(1) - '1' + 1; }

    private int getFileFromName (String name) { return name.toUpperCase().charAt(0) - 'A' + 1; }

    private boolean isInRange(int file, int rank, int fileRangeDown, int fileRangeUp,
                              int rankRangeDown, int rankRangeUp) {
        return file >= fileRangeDown && file <= fileRangeUp && rank >= rankRangeDown && rank <= rankRangeUp;
    }

    @Override
    public int compareTo(Position o) {
        if (o == null)
            return 1;

        if (this.getFile() < o.getFile())
            return -1;
        else if (this.getFile() > o.getFile())
            return 1;
        else
            if (this.getRank() < o.getRank())
                return -1;
            else if (this.getRank() > o.getRank())
                return 1;
            else
                return 0;
    }
}