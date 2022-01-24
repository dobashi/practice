// 座標
class Position {
  constructor(x, y) {
    this.x = x;
    this.y = y;
    this.equals = o => this.x == o.x && this.y == o.y;
    this.toString = () => '(' + this.x + ',' + this.y + ')';
  }
}

