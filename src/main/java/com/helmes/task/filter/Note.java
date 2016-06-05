package com.helmes.task.filter;

import java.io.Serializable;

public class Note implements Comparable, Serializable {

    private String name;
    private String type;
    private String weight;

    public Note(String name, String type, String weight) {
        this.name = name;
        this.type = type;
        this.weight = weight;
    }

    public int compareTo(Object o) {
        Note tmp = (Note)o;
        if(this.name.compareTo(tmp.getName()) == 0) {
            if(this.type.compareTo(tmp.getType()) == 0) {
                int res = this.weight.compareTo(tmp.getWeight());
                if (res != 0) {
                    res *= -1;
                }
                return res;
            }
            else {
                return this.type.compareTo(tmp.getType());
            }
        }
        else {
            return this.name.compareTo(tmp.getName());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (name != null ? !name.equals(note.name) : note.name != null) return false;
        if (type != null ? !type.equals(note.type) : note.type != null) return false;
        return weight != null ? weight.equals(note.weight) : note.weight == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        return result;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
