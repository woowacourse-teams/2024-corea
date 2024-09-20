import { useState } from "react";
import * as S from "@/components/common/img/ImageWithFallback.style";
import { errorCharacter } from "@/assets";

interface ImageWithFallbackProps {
  src: string;
  alt: string;
  fallbackSrc?: string;
}

const ImageWithFallback = ({
  src,
  alt,
  fallbackSrc = errorCharacter,
  ...props
}: ImageWithFallbackProps) => {
  const [isFallback, setIsFallback] = useState(false);

  const handleError = (e: React.SyntheticEvent<HTMLImageElement, Event>) => {
    const img = e.target as HTMLImageElement;
    img.onerror = null;
    img.src = fallbackSrc;
    setIsFallback(true);
  };

  return (
    <S.StyledImg src={src} alt={alt} onError={handleError} $isFallback={isFallback} {...props} />
  );
};

export default ImageWithFallback;
