import styled from "styled-components";
import media from "@/styles/media";

export const BannerContainer = styled.div`
  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 100vw;
  height: 400px;
  margin: -113px calc(-50vw + 50%) 0;

  background: linear-gradient(to bottom, #ff7e5f, #feb47b, ${({ theme }) => theme.COLOR.white});

  ${media.small`
    display:none;
  `}
`;

export const BannerTextContainer = styled.p`
  display: flex;
  flex-direction: column;
`;

export const BannerText = styled.p`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100px;

  font-size: 3.5rem;
  color: ${({ theme }) => theme.COLOR.white};
  text-shadow:
    2px 2px 5px rgb(0 0 0 / 30%),
    4px 4px 10px rgb(0 0 0 / 10%);

  span {
    font-size: 2.5rem;
  }
`;

export const BannerSubText = styled.p`
  margin: 0 auto;
  padding-top: 1rem;

  font: ${({ theme }) => theme.TEXT.medium};
  color: ${({ theme }) => theme.COLOR.grey0};
  text-align: center;
`;

export const Sunlight = styled.div`
  position: absolute;
  background: rgb(255 255 255 / 20%);
  border-radius: 50%;

  &.sun {
    top: -70px;
    right: -30px;

    width: 150px;
    height: 150px;

    background: rgb(255 255 255 / 50%);
    filter: blur(10px);
    box-shadow: 0 0 60px 40px rgb(255 255 255 / 20%);
  }

  &.sunlight-1 {
    top: -110px;
    right: -70px;

    width: 250px;
    height: 250px;

    background: rgb(255 255 255 / 20%);
    filter: blur(10px);
  }

  &.sunlight-2 {
    top: 75px;
    right: 85px;

    width: 40px;
    height: 40px;

    filter: blur(3px);
  }

  &.sunlight-3 {
    top: 135px;
    right: 135px;

    width: 20px;
    height: 20px;

    filter: blur(2px);
  }

  &.sunlight-4 {
    top: 170px;
    right: 160px;

    width: 60px;
    height: 60px;

    filter: blur(3px);
  }

  &.sunlight-5 {
    top: 190px;
    right: 170px;

    width: 100px;
    height: 100px;

    filter: blur(5px);
  }
`;

export const Cloud = styled.img`
  position: absolute;

  &.cloud-1 {
    top: 50px;
    left: 100px;
    height: 100px;
  }

  &.cloud-2 {
    top: 30px;
    left: 220px;
    height: 150px;
  }

  &.cloud-3 {
    top: 150px;
    right: 100px;
    height: 230px;
  }
`;
