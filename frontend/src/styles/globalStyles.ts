import { createGlobalStyle } from "styled-components";

const globalStyles = createGlobalStyle`
  scrollbar-width: none;

  *,
  *::before,
  *::after {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
  }

  html,
  body{
    scrollbar-gutter: stable;
  }

  html,
  body,
  div,
  span,
  applet,
  object,
  iframe,
  h1,
  h2,
  h3,
  h4,
  h5,
  h6,
  p,
  blockquote,
  pre,
  a,
  abbr,
  acronym,
  address,
  big,
  cite,
  code,
  del,
  dfn,
  em,
  img,
  ins,
  kbd,
  q,
  s,
  samp,
  small,
  strike,
  strong,
  sub,
  sup,
  tt,
  var,
  b,
  u,
  i,
  center,
  dl,
  dt,
  dd,
  ol,
  ul,
  li,
  fieldset,
  form,
  label,
  legend,
  table,
  caption,
  tbody,
  tfoot,
  thead,
  tr,
  th,
  td,
  article,
  aside,
  canvas,
  details,
  embed,
  figure,
  figcaption,
  footer,
  header,
  hgroup,
  menu,
  nav,
  output,
  ruby,
  section,
  summary,
  time,
  mark,
  audio,
  video,
  input,
  textarea {
    box-sizing: border-box;
    margin: 0;
    padding: 0;

    font: inherit;
    font-size: 62.5%;
    vertical-align: baseline;

    border: 0;
  }

  /* HTML5 display-role reset for older browsers */
  article,
  aside,
  details,
  figcaption,
  figure,
  footer,
  header,
  hgroup,
  menu,
  nav,
  section {
    display: block;
  }

  body {
    overflow-x: hidden !important;

    font-family: "Noto Sans KR", sans-serif;
    font-size: 100%;
    font-weight: 400;
    font-style: normal;
    line-height: 1;
  }

  ol,
  ul {
    list-style: none;
  }

  blockquote,
  q {
    quotes: none;
  }

  blockquote::before,
  blockquote::after,
  q::before,
  q::after {
    content: '';
    content: none;
  }

  table {
    border-spacing: 0;
    border-collapse: collapse;
  }

  button {
    cursor: pointer;
    border: none;
  }

  a {
    color: inherit;
    text-decoration: none;
  }

  a:hover {
    text-decoration: none;
  }

  a:visited {
    color: inherit;
    text-decoration: none;
  }

  ul,
  li {
    list-style: none;
  }


  ::-webkit-scrollbar {
    width: 7px;
    height: 7px;
  }

  ::-webkit-scrollbar-thumb {
    height: 30%;
    background: rgb(198 198 198 / 70%);
    border-radius: 10px;
  }

  ::-webkit-scrollbar-track {
    background: rgb(198 198 198 / 20%);
  }
`;

export default globalStyles;
