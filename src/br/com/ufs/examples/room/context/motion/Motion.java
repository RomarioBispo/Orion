package br.com.ufs.examples.room.context.motion;

import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.entity.Entity;

public class Motion extends Entity {
    private Attrs count;

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Attrs getCount() {
        return count;
    }

    public void setCount(Attrs count) {
        this.count = count;
    }
}
