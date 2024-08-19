import { useEffect, useState } from "react";
import * as S from "@/components/main/banner/CyclingClasses.style";

const CyclingClasses = ({ items, speed }: { items: string[]; speed: number }) => {
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % items.length);
    }, speed);

    return () => clearInterval(interval);
  }, [items.length]);

  return (
    <S.CyclingContainer>
      <S.CyclingList>
        {items.map((item, index) => {
          let className = "";
          if (index === currentIndex) {
            className = "on";
          } else if (index === (currentIndex - 1 + items.length) % items.length) {
            className = "prev";
          }

          return (
            <li key={index} className={className}>
              {item}
            </li>
          );
        })}
      </S.CyclingList>
    </S.CyclingContainer>
  );
};

export default CyclingClasses;
