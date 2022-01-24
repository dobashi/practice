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

