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


