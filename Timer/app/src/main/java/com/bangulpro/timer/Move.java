package com.bangulpro.timer;

/**
 * Created by IronFactory on 2017. 1. 24..
 */

class Move {
    Point current, target;
    float spdX, spdY, weight, dx, dy;
    int direction;

    public Move(Point current, Point target) {
        this.current = current;
        this.target = target;
    }

    int findDirection() {
        dx = target.x - current.x;
        dy = target.y - current.y;

        if (dx >= 0 && dy >= 0) {           // 우상단
            if (Math.abs(dy) > Math.abs(dx))
                return 0;
            return 1;
        } else if (dx >= 0 && dy <= 0) {    // 우하단
            if (Math.abs(dy) < Math.abs(dx))
                return 2;
            return 3;
        } else if (dx <= 0 && dy <= 0) {    // 좌하단
            if (Math.abs(dy) > Math.abs(dx))
                return 4;
            return 5;
        } else {
            if (Math.abs(dy) < Math.abs(dx))
                return 6;
            return 7;
        }
    }


    Point move() {
        switch (direction) {
            case 0:
                current.y += spdY;
                weight += dx;
                if (weight > dy / 2) {
                    current.x += spdX;
                    weight -= dy;
                }
                break;

            case 1:
                current.x += spdX;
                weight += dy;
                if (weight > dx / 2) {
                    current.y += spdY;
                    weight -= dx;
                }
                break;

            case 2:
                current.x += spdX;
                weight -= dy;
                if (weight > dx / 2) {
                    current.y -= spdY;
                    weight -= dx;
                }
                break;

            case 3:
                current.y -= spdY;
                weight += dx;
                if (weight > -dy / 2) {
                    current.x += spdX;
                    weight -= dy;
                }
                break;

            case 4:
                current.y -= spdY;
                weight -= dx;
                if (weight > -dy / 2) {
                    current.x -= spdX;
                    weight -= dy;
                }
                break;

            case 5:
                current.x -= spdX;
                weight -= dy;
                if (weight > -dx / 2) {
                    current.y -= spdY;
                    weight -= dx;
                }
                break;

            case 6:
                current.x -= spdX;
                weight += dy;
                if (weight > -dx / 2) {
                    current.y -= spdY;
                    weight -= dx;
                }
                break;
        }
        return current;
    }
}
