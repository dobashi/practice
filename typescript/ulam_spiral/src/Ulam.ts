
// Location of number string(x,y)
export class Position {
  x: number
  y: number
  constructor(x, y) {
    this.x = x;
    this.y = y;
  }
  equals = (o: Position) => this.x == o.x && this.y == o.y
  toString = () => '(' + this.x + ',' + this.y + ')'
}


export class Direction {
  name: string
  step: Position
  constructor(name, step) {
    this.name = name;
    this.step = step;
  }
  add = (p) => new Position(p.x + this.step.x, p.y + this.step.y);
}

export class PositionDirection {
  position: Position
  direction: Direction
  constructor(p, d) {
    this.position = p
    this.direction = d
  }
}

class Directions {
  right = new Direction('Right', { x: 1, y: 0 });
  up = new Direction('Ujp', { x: 0, y: 1 });
  left = new Direction('Left', { x: -1, y: 0 });
  down = new Direction('Down', { x: 0, y: -1 });
  directions = [this.right, this.up, this.left, this.down];
  first: PositionDirection = new PositionDirection(new Position(0, 0), this.down)
  next = direction => {
    // console.log(direction)
    const index = this.directions.findIndex(d => d.name == direction.name)
    const next = index == this.directions.length - 1 ? 0 : index + 1
    return this.directions[next];
  }
}
export const directions = new Directions()

// 地図
class Ground {
  _ground: Position[]
  constructor() {
    this._ground = new Array()
  }
  push = (p: Position) => this._ground.push(p)
  find = (p: Position) => this._ground.find(e => e.equals(p));
  next = current => {
    //         console.log("next:current:"+ current.position.toString())
    const p = current.position;
    const d = current.direction;
    //   console.log("next:d:"+ d.name)

    const nd = directions.next(d);
    const np = nd.add(p);

    if (this.find(np)) {
      return { position: d.add(p), direction: d }; // hold current direction
    } else {
      return { position: np, direction: nd };
    }
  }
}
export const ground = new Ground()

const calc = (c, n, interval) => {
  let s, d, v = interval / 2;
  if (c > n) {
    s = c - v;
    d = n + v;
  } else if (c < n) {
    s = c + v;
    d = n - v
  } else {
    s = c;
    d = n;
  }
  return { s, d };
}

// const drawLine = (cpos, npos) => {
//   const c = convert(cpos), n = convert(npos);
//   const x = calc(c.x, n.x, Area.width)
//   const y = calc(c.y, n.y, Area.height)

// $('canvas').drawLine({
//   strokeStyle: '#333333',
//   strokeWidth: 2,
//   rounded: true,
//   endArrow: true,
//   x1: x.s, y1: y.s,
//   x2: x.d, y2: y.d,
// });
// };

class Primes {
  primes: number[]
  constructor(max: number) {
    this.primes = [2, 3]
    this.calc(max)
  }
  calc = (v) => {
    const ps = this.primes
    for (let i = ps[ps.length - 1]; i < v; i += 2) {
      if (ps.find(x => i % x == 0) == undefined) ps.push(i);
    }
  }
  isPrime = (x) => this.primes.includes(x);
}
export const primes = new Primes(1000)


