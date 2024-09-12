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
  const handleError = (e: React.SyntheticEvent<HTMLImageElement, Event>) => {
    const img = e.target as HTMLImageElement;
    img.onerror = null;
    img.src = fallbackSrc;
  };

  return <S.StyledImg src={src} alt={alt} onError={handleError} {...props} />;
};

export default ImageWithFallback;
