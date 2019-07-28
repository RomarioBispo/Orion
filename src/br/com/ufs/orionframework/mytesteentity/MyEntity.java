package br.com.ufs.orionframework.mytesteentity;

import br.com.ufs.orionframework.entity.Entity;
import br.com.ufs.orionframework.entity.Attrs;

public class MyEntity extends Entity {
    private Attrs attribute1;
    private Attrs attribute2;
    private Attrs attribute3;
    private Attrs attribute4;
    private Attrs attribute5;

    public MyEntity(String id, String type, Attrs attribute1, Attrs attribute2, Attrs attribute3, Attrs attribute4, Attrs attribute5) {
        this.id = id;
        this.type = type;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
        this.attribute4 = attribute4;
        this.attribute5 = attribute5;
    }

    public MyEntity() {
    }

    public Attrs getAttribute4() {
        return attribute4;
    }

    public Attrs getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(Attrs attribute1) {
        this.attribute1 = attribute1;
    }

    public Attrs getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(Attrs attribute2) {
        this.attribute2 = attribute2;
    }

    public Attrs getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(Attrs attribute3) {
        this.attribute3 = attribute3;
    }

    public void setAttribute4(Attrs attribute4) {
        this.attribute4 = attribute4;
    }

    public Attrs getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(Attrs attribute5) {
        this.attribute5 = attribute5;
    }

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
}
