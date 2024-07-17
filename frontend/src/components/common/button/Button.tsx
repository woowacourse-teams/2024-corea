import * as S from "@/components/common/button/Button.style";

interface ButtonProps
  extends React.PropsWithChildren<React.ButtonHTMLAttributes<HTMLButtonElement>> {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
  text: string;
  color?: "primary2" | "secondary" | "grey4";
}

const Button = ({ text, onClick, color = "primary2", ...props }: ButtonProps) => {
  return (
    <S.Button onClick={onClick} color={color} {...props}>
      {text}
    </S.Button>
  );
};

export default Button;
