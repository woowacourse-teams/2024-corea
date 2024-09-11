import { ReactNode, useEffect, useState } from "react";

const DelaySuspense = ({ children }: { children: ReactNode }) => {
  const [show, setShow] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      setShow(true);
    }, 500);

    return () => clearTimeout(timer);
  }, []);

  return show ? children : null;
};

export default DelaySuspense;
