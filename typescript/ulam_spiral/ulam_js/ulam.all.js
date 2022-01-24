// 方向
class Direction {
  constructor(name, step) {
    this.name = name;
    this.step = step;
    this.add = (p) => new Position(p.x + step.x, p.y + step.y);
  }
}

const Right = new Direction('Right', {x:1, y:0});
const Up = new Direction('Ujp',  {x:0, y:1});
const Left = new Direction('Left',  {x:-1, y:0});
const Down = new Direction('Down',  {x:0, y:-1});

let directions = [Right, Up, Left, Down];
let nextDirection = direction => {
        console.log(direction)
  let index = directions.findIndex(d => d.name == direction.name);
  let next = index == directions.length - 1 ? 0 : index + 1;
  return directions[next];
};

// 地図
let __ground = [];
const groundPut = p => __ground.push(Object.assign({}, p));
const groundFind = p => __ground.find(e => e.equals(p));

// 次位置探し
const groundNext = current => {
//         console.log("next:current:"+ current.position.toString())
  let p = current.position;
  let d = current.direction;
//   console.log("next:d:"+ d.name)

  let nd = nextDirection(d);
  let np = nd.add(p);

  if (groundFind(np)) {
    // 次の方向に曲がったときのポジションが埋まってるなら
    return { position: d.add(p), direction: d }; // 現在の方向へ継続
  } else {
    return { position: np, direction: nd };
  }
};

const calc = (c,n,interval) => {
  let s, d, v=interval/2;
  if(c > n){
    s = c - v;
    d = n + v;
  }else if(c < n){
    s = c + v;
    d = n - v
  }else{
    s = c;
    d = n;
  }
  return {s, d};
}

const drawLine = (cpos, npos) => {
  const c = convert(cpos), n=convert(npos);
  const x = calc(c.x, n.x, Area.width)
  const y = calc(c.y, n.y, Area.height)

  $('canvas').drawLine({
    strokeStyle: '#333333',
    strokeWidth: 2,
    rounded: true,
    endArrow: true,
    x1: x.s, y1: y.s,
    x2: x.d, y2: y.d,
  });
};

// 座標
class Position {
  constructor(x, y) {
    this.x = x;
    this.y = y;
    this.equals = o => this.x == o.x && this.y == o.y;
    this.toString = () => '(' + this.x + ',' + this.y + ')';
  }
}

let __primes =[2,3];
const calcPrime = (v) => {
  // 計算済みものより大きい数値だけ再計算
  for(let i = __primes[__primes.length-1]; i<v; i+=2){
    if(__primes.find(x => i%x==0) == undefined) __primes.push(i);
  }
}
const isPrime = (x) => __primes.includes(x);

const offset = { x: canvas.width/2, y: canvas.height/2 };
const Area = { width: 50, height: 30, interval: 10 };
const wi = Area.width + Area.interval;
const hi = Area.height + Area.interval;
const convert = pos => new Position(pos.x * wi + offset.x, pos.y * hi * -1 + offset.y);
const box = (p, c) => {
  $('canvas').drawRect({
    fillStyle: c,
    x: p.x, y: p.y,
    width: Area.width, height: Area.height,
  });
};
const drawPos = (p, s, c) => {
  $('canvas').drawText({
    fillStyle: c,
    x: p.x, y: p.y,
    text: s,
  });
};

const startColor = { fg: '#FFFFFF', bg:'#00CC33' }
const normalColor = { fg: '#000000', bg:'#F0F0FF' }
const primeColor = { fg: '#FFFFFF', bg:'#CC2010' }

const draw = (i, c) => {
  const color = (i==1)?startColor:isPrime(i)?primeColor:normalColor;
  let dp = convert(c.position);
  box(dp, color.bg);
  drawPos(dp, i, color.fg);
};


