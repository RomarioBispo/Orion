package myTesteEntity;

import entity.Entity;
import entityLamp.Attrs;

public class MyEntity extends Entity {
    private Attrs attribute4;
    private Attrs attribute5;

    public MyEntity(String id, String type, Attrs attribute1, Attrs attribute2, Attrs attribute3, Attrs attribute4, Attrs attribute5) {
        super(id, type, attribute1, attribute2, attribute3);
        this.attribute4 = attribute4;
        this.attribute5 = attribute5;
    }

    public Attrs getAttribute4() {
        return attribute4;
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
}
