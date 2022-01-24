const urlencoder_state = {
  text: '',
}

const urlencoder_actions = {
  text: value => urlencoder_state => ({ text: encodeURIComponent(value) })
}

const urlencoder_view = (state, actions) =>
  h("div", { id:"urlencoder"}, [
    h("label", {} ,"URLエンコードしたい文字列"),
    h("br"),
    h("textarea", { id: "urlencoder_area", rows: 5, placeholder: "Input URL", onkeyup: ev => actions.text(ev.target.value) }, ""),
    h("div", { class:"target"},[
    h("h3", { id: "label", class: "encode_result" }, state.text),
    //h("br"),
    ]),
  ])
  

let urlencoder = () => window.main = app(urlencoder_state, urlencoder_actions, urlencoder_view, byId("contents"))