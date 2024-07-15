import { useState } from "react";

const useTest = () => {
  const [data, setData] = useState(1);

  const func = (val: number) => {
    setData(val);
  };

  return { data, func };
};

export default useTest;
