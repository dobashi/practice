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

