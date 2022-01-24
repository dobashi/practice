let __primes =[2,3];
const calcPrime = (v) => {
  // 計算済みものより大きい数値だけ再計算
  for(let i = __primes[__primes.length-1]; i<v; i+=2){
    if(__primes.find(x => i%x==0) == undefined) __primes.push(i);
  }
}
const isPrime = (x) => __primes.includes(x);

