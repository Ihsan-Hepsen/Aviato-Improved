package ih.ifbs.domain;

public abstract class EntityClass {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityClass entityClass)) return false;

        return id == entityClass.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
