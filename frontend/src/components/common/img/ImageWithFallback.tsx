import * as S from "@/components/common/img/ImageWithFallback.style";
import { errorCharacterBase64 } from "@/assets/character/error-character-base64";

interface ImageWithFallbackProps {
  src: string;
  alt: string;
  fallbackSrc?: string;
}

const ImageWithFallback = ({
  src,
  alt,
  fallbackSrc = errorCharacterBase64,
  ...props
}: ImageWithFallbackProps) => {
  const handleError = (e: React.SyntheticEvent<HTMLImageElement, Event>) => {
    const img = e.target as HTMLImageElement;
    img.onerror = null;
    img.src = fallbackSrc;
  };

  return (
    <S.StyledImg
      src={src ? src : fallbackSrc}
      alt={alt}
      onError={handleError}
      $isFallback={!src}
      {...props}
    />
  );
};

export default ImageWithFallback;
