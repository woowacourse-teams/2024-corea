import { useState } from "react";

const oneSecond = 1000;

const useCountDown = (second: number) => {
  const [remainingTime, setRemainingTime] = useState(0);
  const [isRefreshing, setIsRefreshing] = useState(false);

  const startRefreshCountDown = () => {
    setIsRefreshing(true);
    setRemainingTime(second);

    const timer = setInterval(() => {
      setRemainingTime((prevTime) => {
        if (prevTime <= 1) {
          clearInterval(timer);
          setIsRefreshing(false);
          return 0;
        }
        return prevTime - 1;
      });
    }, oneSecond);
  };

  return { remainingTime, isRefreshing, startRefreshCountDown };
};

export default useCountDown;
